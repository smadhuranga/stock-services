module.exports = {
  apps : [{
    name       : "cloud-sql-auth-proxy",
    script     : "./cloud-sql-proxy cloudproject-490706:asia-southeast1:mysql-vm --private-ip",
    out_file   : "./logs/cloud-sql-proxy.log",
    error_file : "./logs/cloud-sql-proxy-error.log",
    merge_logs : true
  },
  {
    name       : "stock-service",
    script     : "java -jar ./stock-service/target/stock-service-0.0.1-SNAPSHOT.jar",
    out_file   : "./logs/stock-service.log",
    error_file : "./logs/stock-service-error.log",
    merge_logs : true,
    instances  : 2
  },
  {
    name       : "supplier-service",
    script     : "java -jar ./supplier-service/target/supplier-service-0.0.1-SNAPSHOT.jar",
    out_file   : "./logs/supplier-service.log",
    error_file : "./logs/supplier-service-error.log",
    merge_logs : true,
    instances  : 2
  },
  {
    name       : "file-service",
    script     : "java -jar ./file-service/target/file-service-0.0.1-SNAPSHOT.jar",
    out_file   : "./logs/file-service.log",
    error_file : "./logs/file-service-error.log",
    merge_logs : true,
    instances  : 2
  }]
}
