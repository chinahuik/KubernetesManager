-- MySQL dump 10.13  Distrib 5.6.43, for Linux (x86_64)
--
-- Host: localhost    Database: kubecloud
-- ------------------------------------------------------
-- Server version	5.6.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


--
-- Table structure for table `base_image`
--

DROP TABLE IF EXISTS `base_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_image` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `image_host` varchar(255) NOT NULL,
  `image_repo` varchar(255) NOT NULL,
  `image_tag` varchar(255) NOT NULL,
  `operating_system` varchar(50) DEFAULT NULL,
  `os_version` varchar(50) DEFAULT NULL,
  `develop_language` varchar(50) DEFAULT NULL,
  `dev_lang_version` varchar(50) DEFAULT NULL,
  `command` varchar(255) DEFAULT NULL,
  `start_args` tinytext,
  `work_dir` varchar(255) DEFAULT NULL,
  `port_expose` varchar(255) DEFAULT NULL,
  `labels` tinytext,
  `size` bigint(20) DEFAULT NULL,
  `layers` int(11) DEFAULT NULL,
  `dockerfile` text,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `image_host` (`image_host`),
  KEY `image_repo` (`image_repo`),
  KEY `image_tag` (`image_tag`),
  KEY `operating_system` (`operating_system`),
  KEY `os_version` (`os_version`),
  KEY `develop_language` (`develop_language`),
  KEY `dev_lang_version` (`dev_lang_version`),
  KEY `image_host_2` (`image_host`,`image_repo`,`image_tag`),
  KEY `operating_system_2` (`operating_system`,`os_version`),
  KEY `develop_language_2` (`develop_language`,`dev_lang_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_image`
--

LOCK TABLES `base_image` WRITE;
/*!40000 ALTER TABLE `base_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `base_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `base_table`
--

DROP TABLE IF EXISTS `base_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_table` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_table`
--

LOCK TABLES `base_table` WRITE;
/*!40000 ALTER TABLE `base_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `base_table` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `kube_cluster`
--

DROP TABLE IF EXISTS `kube_cluster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_cluster` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `name_cn` varchar(255) DEFAULT NULL,
  `server_host` varchar(255) DEFAULT NULL,
  `tiller_host` varchar(255) DEFAULT NULL,
  `server_port` int(11) DEFAULT NULL,
  `tiller_port` int(11) DEFAULT NULL,
  `config` text,
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `name_cn` (`name_cn`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_cluster`
--

LOCK TABLES `kube_cluster` WRITE;
/*!40000 ALTER TABLE `kube_cluster` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_cluster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kube_container`
--

DROP TABLE IF EXISTS `kube_container`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_container` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `cluster_id` int(10) unsigned DEFAULT NULL,
  `container_type` varchar(20) DEFAULT NULL,
  `pod_name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `resources` text,
  `cpu_request` bigint(20) DEFAULT NULL,
  `memory_request` bigint(20) DEFAULT NULL,
  `gpu_request` bigint(20) DEFAULT NULL,
  `gmem_request` bigint(20) DEFAULT NULL,
  `cpu_limit` bigint(20) DEFAULT NULL,
  `memory_limit` bigint(20) DEFAULT NULL,
  `gpu_limit` bigint(20) DEFAULT NULL,
  `gmem_limit` bigint(20) DEFAULT NULL,
  `volume_mounts` text,
  `image_pull_policy` varchar(20) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `ready` tinyint(1) DEFAULT NULL,
  `restart_count` int(11) DEFAULT NULL,
  `image_id` varchar(255) DEFAULT NULL,
  `container_id` varchar(100) DEFAULT NULL,
  `started` tinyint(1) DEFAULT NULL,
  `phase` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `last_terminated_exit_code` int(11) DEFAULT NULL,
  `last_terminated_reason` varchar(50) DEFAULT NULL,
  `last_terminated_start_at` datetime DEFAULT NULL,
  `last_terminated_finished_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `cluster_id` (`cluster_id`),
  KEY `name` (`name`),
  KEY `namespace` (`namespace`),
  KEY `pod_name` (`pod_name`),
  KEY `phase` (`phase`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_container`
--

LOCK TABLES `kube_container` WRITE;
/*!40000 ALTER TABLE `kube_container` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_container` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kube_daemonset`
--

DROP TABLE IF EXISTS `kube_daemonset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_daemonset` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `cluster_id` int(10) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `resource_version` varchar(255) DEFAULT NULL,
  `annotations` text,
  `self_link` varchar(255) DEFAULT NULL,
  `labels` text,
  `revision_history_limit` int(11) DEFAULT NULL,
  `update_strategy` text,
  `selector` text,
  `template` text,
  `current_number_scheduled` int(11) DEFAULT NULL,
  `desired_number_scheduled` int(11) DEFAULT NULL,
  `number_misscheduled` int(11) DEFAULT NULL,
  `number_ready` int(11) DEFAULT NULL,
  `number_unavailable` int(11) DEFAULT NULL,
  `observed_generation` int(11) DEFAULT NULL,
  `updated_number_scheduled` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `uid` (`uid`),
  KEY `cluster_id` (`cluster_id`),
  KEY `name` (`name`),
  KEY `self_link` (`self_link`),
  KEY `namespace` (`namespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_daemonset`
--

LOCK TABLES `kube_daemonset` WRITE;
/*!40000 ALTER TABLE `kube_daemonset` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_daemonset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kube_deployment`
--

DROP TABLE IF EXISTS `kube_deployment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_deployment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `cluster_id` int(10) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `resource_version` varchar(255) DEFAULT NULL,
  `annotations` text,
  `self_link` varchar(255) DEFAULT NULL,
  `labels` text,
  `generation` int(11) DEFAULT NULL,
  `replicas` int(11) DEFAULT NULL,
  `select_labels` text,
  `strategy_type` varchar(50) DEFAULT NULL,
  `revision_history_limit` int(11) DEFAULT NULL,
  `progress_deadline_seconds` int(11) DEFAULT NULL,
  `observed_generation` int(11) DEFAULT NULL,
  `updated_replicas` int(11) DEFAULT NULL,
  `ready_replicas` int(11) DEFAULT NULL,
  `available_replicas` int(11) DEFAULT NULL,
  `available` varchar(10) DEFAULT NULL,
  `available_reason` varchar(50) DEFAULT NULL,
  `available_message` varchar(255) DEFAULT NULL,
  `progressing` varchar(10) DEFAULT NULL,
  `progressing_reason` varchar(50) DEFAULT NULL,
  `progressing_message` varchar(255) DEFAULT NULL,
  `template` text,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `uid` (`uid`),
  KEY `cluster_id` (`cluster_id`),
  KEY `name` (`name`),
  KEY `self_link` (`self_link`),
  KEY `namespace` (`namespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_deployment`
--

LOCK TABLES `kube_deployment` WRITE;
/*!40000 ALTER TABLE `kube_deployment` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_deployment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kube_ingress`
--

DROP TABLE IF EXISTS `kube_ingress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_ingress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `cluster_id` int(10) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `resource_version` varchar(255) DEFAULT NULL,
  `annotations` text,
  `self_link` varchar(255) DEFAULT NULL,
  `labels` text,
  `rules` text,
  `tls` text,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `uid` (`uid`),
  KEY `cluster_id` (`cluster_id`),
  KEY `name` (`name`),
  KEY `self_link` (`self_link`),
  KEY `namespace` (`namespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_ingress`
--

LOCK TABLES `kube_ingress` WRITE;
/*!40000 ALTER TABLE `kube_ingress` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_ingress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kube_job`
--

DROP TABLE IF EXISTS `kube_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_job` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `cluster_id` int(10) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `resource_version` varchar(255) DEFAULT NULL,
  `annotations` text,
  `self_link` varchar(255) DEFAULT NULL,
  `labels` text,
  `backoff_limit` int(11) DEFAULT NULL,
  `completions` int(11) DEFAULT NULL,
  `parallelism` int(11) DEFAULT NULL,
  `selector` text,
  `template` text,
  `completion_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `succeeded` int(11) DEFAULT NULL,
  `complete_status` varchar(20) DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `uid` (`uid`),
  KEY `cluster_id` (`cluster_id`),
  KEY `name` (`name`),
  KEY `self_link` (`self_link`),
  KEY `namespace` (`namespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_job`
--

LOCK TABLES `kube_job` WRITE;
/*!40000 ALTER TABLE `kube_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kube_namespace`
--

DROP TABLE IF EXISTS `kube_namespace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_namespace` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `self_link` varchar(500) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `resource_version` varchar(20) DEFAULT NULL,
  `status_phase` varchar(20) DEFAULT NULL,
  `cluster_id` int(11) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `namespace` (`namespace`),
  KEY `self_link` (`self_link`(255)),
  KEY `uid` (`uid`),
  KEY `status_phase` (`status_phase`),
  KEY `cluster_id` (`cluster_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_namespace`
--

LOCK TABLES `kube_namespace` WRITE;
/*!40000 ALTER TABLE `kube_namespace` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_namespace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kube_node`
--

DROP TABLE IF EXISTS `kube_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_node` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `cluster_id` int(10) unsigned DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `resource_version` varchar(255) DEFAULT NULL,
  `annotations` text,
  `self_link` varchar(255) DEFAULT NULL,
  `labels` text,
  `pod_cidr` varchar(50) DEFAULT NULL,
  `cpu_cap` bigint(11) DEFAULT NULL,
  `gpu_cap` int(11) DEFAULT NULL,
  `memory_cap` bigint(20) DEFAULT NULL,
  `gmem_cap` bigint(20) DEFAULT NULL,
  `pods_cap` int(11) DEFAULT NULL,
  `hugepages1g_cap` int(11) DEFAULT NULL,
  `hugepages2m_cap` int(11) DEFAULT NULL,
  `ephemeral_storage_cap` bigint(20) DEFAULT NULL,
  `cpu_allocatable` bigint(11) DEFAULT NULL,
  `memory_allocatable` bigint(20) DEFAULT NULL,
  `gpu_allocatable` bigint(20) DEFAULT NULL,
  `gmem_allocatable` bigint(20) DEFAULT NULL,
  `pods_allocatable` int(11) DEFAULT NULL,
  `hugepages1g_allocatable` int(11) DEFAULT NULL,
  `hugepages2m_allocatable` int(11) DEFAULT NULL,
  `ephemeral_storage_allocatable` bigint(20) DEFAULT NULL,
  `internal_ip` varchar(20) DEFAULT NULL,
  `host_name` varchar(50) DEFAULT NULL,
  `machine_id` varchar(50) DEFAULT NULL,
  `boot_id` varchar(50) DEFAULT NULL,
  `kernel_version` varchar(50) DEFAULT NULL,
  `container_runtime_version` varchar(50) DEFAULT NULL,
  `kube_version` varchar(50) DEFAULT NULL,
  `system_uuid` varchar(50) DEFAULT NULL,
  `kube_proxy_version` varchar(50) DEFAULT NULL,
  `operating_system` varchar(50) DEFAULT NULL,
  `os_image` varchar(50) DEFAULT NULL,
  `architecture` varchar(20) DEFAULT NULL,
  `network_unavailable` varchar(10) DEFAULT NULL,
  `network_reason` varchar(50) DEFAULT NULL,
  `memory_pressure` varchar(10) DEFAULT NULL,
  `memory_reason` varchar(50) DEFAULT NULL,
  `network_message` varchar(255) DEFAULT NULL,
  `memory_message` varchar(255) DEFAULT NULL,
  `disk_pressure` varchar(10) DEFAULT NULL,
  `disk_reason` varchar(50) DEFAULT NULL,
  `disk_message` varchar(255) DEFAULT NULL,
  `pid_pressure` varchar(10) DEFAULT NULL,
  `pid_reason` varchar(50) DEFAULT NULL,
  `pid_message` varchar(255) DEFAULT NULL,
  `kube_ready` varchar(10) DEFAULT NULL,
  `kube_reason` varchar(50) DEFAULT NULL,
  `kube_message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `uid` (`uid`),
  KEY `cluster_id` (`cluster_id`),
  KEY `name` (`name`),
  KEY `self_link` (`self_link`),
  KEY `internal_ip` (`internal_ip`),
  KEY `host_name` (`host_name`),
  KEY `machine_id` (`machine_id`),
  KEY `boot_id` (`boot_id`),
  KEY `kernel_version` (`kernel_version`),
  KEY `container_runtime_version` (`container_runtime_version`),
  KEY `kube_version` (`kube_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_node`
--

LOCK TABLES `kube_node` WRITE;
/*!40000 ALTER TABLE `kube_node` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kube_pod`
--

DROP TABLE IF EXISTS `kube_pod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_pod` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `cluster_id` int(10) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `resource_version` varchar(255) DEFAULT NULL,
  `owner_references` text,
  `self_link` varchar(255) DEFAULT NULL,
  `labels` text,
  `restart_policy` varchar(20) DEFAULT NULL,
  `termination_grace_period_seconds` int(11) DEFAULT NULL,
  `dns_policy` varchar(50) DEFAULT NULL,
  `service_account_name` varchar(50) DEFAULT NULL,
  `service_account` varchar(50) DEFAULT NULL,
  `node_name` varchar(50) DEFAULT NULL,
  `scheduler_name` varchar(50) DEFAULT NULL,
  `security_context` text,
  `tolerations` text,
  `enable_service_links` tinyint(1) DEFAULT NULL,
  `phase` varchar(50) DEFAULT NULL,
  `host_ip` varchar(20) DEFAULT NULL,
  `pod_ip` varchar(20) DEFAULT NULL,
  `qos_class` varchar(20) DEFAULT NULL,
  `initialized` varchar(10) DEFAULT NULL,
  `initialized_update_time` datetime DEFAULT NULL,
  `pod_ready` varchar(10) DEFAULT NULL,
  `pod_ready_update_time` datetime DEFAULT NULL,
  `containers_ready` varchar(10) DEFAULT NULL,
  `containers_ready_update_time` datetime DEFAULT NULL,
  `pod_scheduled` varchar(10) DEFAULT NULL,
  `pod_scheduled_update_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `uid` (`uid`),
  KEY `cluster_id` (`cluster_id`),
  KEY `name` (`name`),
  KEY `self_link` (`self_link`),
  KEY `host_ip` (`host_ip`),
  KEY `node_name` (`node_name`),
  KEY `phase` (`phase`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_pod`
--

LOCK TABLES `kube_pod` WRITE;
/*!40000 ALTER TABLE `kube_pod` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_pod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kube_service`
--

DROP TABLE IF EXISTS `kube_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kube_service` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `cluster_id` int(10) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `resource_version` varchar(255) DEFAULT NULL,
  `annotations` text,
  `self_link` varchar(255) DEFAULT NULL,
  `labels` text,
  `ports` text,
  `selector` text,
  `cluster_ip` varchar(20) DEFAULT NULL,
  `service_type` varchar(20) DEFAULT NULL,
  `session_affinity` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `uid` (`uid`),
  KEY `cluster_id` (`cluster_id`),
  KEY `name` (`name`),
  KEY `self_link` (`self_link`),
  KEY `namespace` (`namespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kube_service`
--

LOCK TABLES `kube_service` WRITE;
/*!40000 ALTER TABLE `kube_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `kube_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `node_image`
--

DROP TABLE IF EXISTS `node_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `node_image` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `node_id` int(11) DEFAULT NULL,
  `node_name` varchar(50) DEFAULT NULL,
  `image_id` int(11) DEFAULT NULL,
  `image_name` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`),
  KEY `node_id` (`node_id`),
  KEY `node_name` (`node_name`),
  KEY `image_id` (`image_id`),
  KEY `image_name` (`image_name`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `node_image`
--

LOCK TABLES `node_image` WRITE;
/*!40000 ALTER TABLE `node_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `node_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_config`
--

DROP TABLE IF EXISTS `system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(10) unsigned NOT NULL,
  `config_name` varchar(45) DEFAULT NULL,
  `config_value` tinytext,
  `description` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_config`
--

LOCK TABLES `system_config` WRITE;
/*!40000 ALTER TABLE `system_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_config_group`
--

DROP TABLE IF EXISTS `system_config_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_config_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(45) DEFAULT NULL,
  `description` tinytext,
  `created_by` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_config_group`
--

LOCK TABLES `system_config_group` WRITE;
/*!40000 ALTER TABLE `system_config_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_config_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created_by` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `pass_md5` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `created_by` (`created_by`),
  KEY `update_time` (`update_time`),
  KEY `updated_by` (`updated_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-02 15:58:29
