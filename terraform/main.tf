# TODO provide the required config like project-id, resources etc
provider "google" {
  project = "project-id"
  region  = "europe-west3"
}

resource "google_container_cluster" "realestate_cluster" {
  name     = "realestate-cluster"
  location = "europe-west3"
  initial_node_count = 3
  node_config {
    machine_type = "e2-medium"
    disk_size_gb = 30
    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
  }
}

resource "google_sql_database_instance" "realestate_db" {
  name             = "realestate-db"
  database_version = "POSTGRES_13"
  region           = "europe-west3"
  settings {
    tier = "db-f1-micro"
  }
}

resource "google_sql_database" "realestate_db" {
  name     = "realestate_db"
  instance = google_sql_database_instance.realestate_db.name
}

resource "google_sql_user" "realestate_user" {
  name     = "postgres"
  instance = google_sql_database_instance.realestate_db.name
  password = "admin"
}

resource "kubernetes_deployment" "realestate_app" {
  metadata {
    name = "realestate-app"
    labels = {
      app = "realestate"
    }
  }
  spec {
    replicas = 2
    selector {
      match_labels = {
        app = "realestate"
      }
    }
    template {
      metadata {
        labels = {
          app = "realestate"
        }
      }
      spec {
        container {
          image = "gcr.io/your-gcp-project-id/real-estate-platform:latest"
          name  = "realestate-app"
          port {
            container_port = 8080
          }
          env {
            name  = "SPRING_DATASOURCE_URL"
            value = "jdbc:postgresql://realestate-db:5432/realestate_db"
          }
        }
      }
    }
  }
}