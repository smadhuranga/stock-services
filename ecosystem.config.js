module.exports = {
  apps : [{
    name       : "stock-service",
    script     : "java -jar ./stock-service/target/stock-service-0.0.1-SNAPSHOT.jar",
    out_file   : "./logs/stock-service.log",
    error_file : "./logs/stock-service-error.log",
    merge_logs : true
  },
  {
    name       : "supplier-service",
    script     : "java -jar ./supplier-service/target/supplier-service-0.0.1-SNAPSHOT.jar",
    out_file   : "./logs/supplier-service.log",
    error_file : "./logs/supplier-service-error.log",
    merge_logs : true
  },
  {
    name       : "file-service",
    script     : "java -jar ./file-service/target/file-service-0.0.1-SNAPSHOT.jar",
    out_file   : "./logs/file-service.log",
    error_file : "./logs/file-service-error.log",
    merge_logs : true
  }]
}
