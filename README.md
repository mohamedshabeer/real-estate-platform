# 🏡 Real Estate Management Platform  

*A simple reference implemention for property listings, cloud deployments, and (scalable - to be scaled) real estate operations*  

---

## 🚀 Key Features  

### **Property Management Made Simple**  
- Add, edit, or remove listings with RESTful APIs  
- Automatic schema updates using **Flyway migrations**  

### **Deploy Anywhere**  
- 🐳 Run locally with Docker Compose for quick testing  
- ☸️ Scale in production with Kubernetes (GKE)  
- 🔒 Secure cloud PostgreSQL via Google Cloud SQL  

### **DevOps-Ready**  
- GitHub Actions for automated CI/CD pipelines  
- Terraform scripts to spin up GCP infrastructure in minutes  
- Helm charts for Kubernetes deployments (optional)  

### **Visibility & Monitoring**  
- Track app health with Spring Boot Actuator metrics  
- Inspect databases visually using pgAdmin  
- Debug Kubernetes pods with real-time logs  

---

## 🛠 Tech Stack  

**Backend Framework**  
> Java 21 • Spring Boot 3 • Spring Data JPA  

**Database**  
> PostgreSQL (local dev) • Google Cloud SQL (production)  

**Infrastructure**  
> Docker • Kubernetes (GKE) • Terraform  

**Tools**  
> Flyway • pgAdmin • Helm • RestAssured  

---

## 🚀 Quick Start Guide  

### **Option 1: Local Development with Docker**  

1. **Build the app**  
   ```bash  
   mvn clean package -DskipTests  # Skip tests for faster setup  
   ```  

2. **Spin up containers**  
   ```bash  
   docker-compose up --build -d  # Runs app, PostgreSQL, and pgAdmin  
   ```  

3. **Access services**  
   - App: http://localhost:8080  
   - pgAdmin: http://localhost:5050 (use `admin@admin.com` / `admin`)  

4. **Tear down**  
   ```bash  
   docker-compose down  # Clean up when done  
   ```  

---

### **Option 2: Production Deployment on GCP**  

1. **Initialize Terraform**  
   ```bash  
   terraform init  # Sets up GCP provider and modules  
   ```  

2. **Deploy infrastructure**  
   ```bash  
   terraform apply -auto-approve  # Creates GKE cluster + Cloud SQL  
   ```  

3. **Check Kubernetes pods**  
   ```bash  
   kubectl get pods  # Verify your app is running  
   ```  

4. **Need to clean up?**  
   ```bash  
   terraform destroy -auto-approve  # Deletes all GCP resources  
   ```  

---

## 📡 API Examples  

**Get all properties**  
```http  
GET /properties  
Response: [  
  {  
    "id": 1,  
    "title": "Luxury Villa",  
    "price": 1500000,  
    "location": "Dubai"  
  }  
]  
```  

**Create a new listing**  
```http  
POST /properties  
Body: {  
  "title": "Mountain Cabin",  
  "price": 299000,  
  "bedrooms": 3  
}  

Response: 201 Created + New property JSON  
```  

**Delete a listing**  
```http  
DELETE /properties/3  # Replace "3" with the property ID  
Response: 204 No Content  
```  

*Tip: Use the included Postman collection for full API workflows!*  

---

## 🧰 Advanced: Helm Charts (Optional)  

Customize your Kubernetes deployment:  
```bash  
helm install real-estate-platform helm-charts/  # Deploy  
helm upgrade real-estate-platform helm-charts/  # Update configs  
helm uninstall real-estate-platform  # Remove from cluster  
```  

---

## 📜 License  

MIT License — use freely, but buy me a coffee :-)