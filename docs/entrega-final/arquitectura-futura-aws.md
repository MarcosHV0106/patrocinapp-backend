# Arquitectura futura AWS

> Propuesta de escalamiento futuro, no desplegada en el MVP.

Una evolución posible: Angular en S3/CloudFront; API en contenedores ECS/EKS detrás de ALB y Auto Scaling; PostgreSQL RDS Multi-AZ; evidencias en S3 mediante URLs firmadas; secretos en Secrets Manager; métricas/logs/alarmas en CloudWatch; WAF, backups y réplicas según RPO/RTO.

```mermaid
flowchart LR
  U[Usuario] --> CF[CloudFront + WAF]
  CF --> S3F[S3 frontend]
  U --> ALB[Application Load Balancer]
  ALB --> APP[ECS/EKS API, múltiples réplicas]
  APP --> RDS[(RDS PostgreSQL Multi-AZ)]
  APP --> S3E[(S3 evidencias)]
  APP --> SM[Secrets Manager]
  APP --> CW[CloudWatch]
```

No hay manifiestos Kubernetes aplicados, recursos AWS, SLA ni pruebas de alta disponibilidad actuales.
