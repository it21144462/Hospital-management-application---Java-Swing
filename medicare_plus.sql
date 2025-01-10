-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2025 at 03:44 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `medicare_plus`
--

-- --------------------------------------------------------

--
-- Table structure for table `channel`
--

CREATE TABLE `channel` (
  `channel_no` varchar(11) NOT NULL,
  `patientname` varchar(255) NOT NULL,
  `doctorname` varchar(255) NOT NULL,
  `room_no` int(11) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `channel`
--

INSERT INTO `channel` (`channel_no`, `patientname`, `doctorname`, `room_no`, `date`) VALUES
('C001', 'PS001', 'D001', 3, '2024-12-24'),
('C003', 'PS004', 'D001', 3, '2024-12-24'),
('C004', 'PS001', 'D005', 105, '2024-12-24'),
('C005', 'PS001', 'D001', 3, '2024-01-01'),
('C006', 'PS002', 'D004', 61, '2024-01-01'),
('C007', 'PS001', 'D003', 107, '2024-12-24'),
('C009', 'PS001', 'D001', 3, '2024-01-05'),
('C010', 'PS001', 'D001', 3, '2024-01-04'),
('C011', 'PS006', 'D005', 105, '2024-12-19'),
('C012', 'PS001', 'D001', 3, '2024-01-01'),
('C013', 'PS002', 'D002', 7, '2024-07-07'),
('C014', 'PS002', 'D005', 105, '2024-08-29'),
('C015', 'PS006', 'D003', 107, '2033-03-06'),
('C016', 'PS008', 'D003', 107, '2024-03-24'),
('C017', 'PS002', 'D003', 107, '2024-11-23'),
('C018', 'PS006', 'D004', 61, '2030-01-04'),
('C019', 'PS002', 'D002', 7, '2029-01-06');

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `doctorno` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `specialization` varchar(255) NOT NULL,
  `qualification` varchar(255) NOT NULL,
  `channel_fee` int(11) NOT NULL,
  `phone` int(20) NOT NULL,
  `room_no` int(11) NOT NULL,
  `log_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`doctorno`, `name`, `specialization`, `qualification`, `channel_fee`, `phone`, `room_no`, `log_id`) VALUES
('D001', 'Dr. John Doe', 'Cardiologist', 'MBBS, MD', 5000, 771234567, 3, 23),
('D002', 'Dr. Jane Smith', 'Dermatologist', 'MBBS, D.Derm', 4000, 779876543, 7, 31),
('D003', 'Dr. Alan Brown', 'Pediatrician', 'MBBS, MD(Ped)', 3500, 712345678, 107, 32),
('D004', 'Dr. Emily White', 'Neurologist', 'MBBS, DM(Neuro)', 6000, 718765432, 61, 33),
('D005', 'Dr. Michael Green', 'Orthopedic Surgeon', 'MBBS, MS(Ortho)', 4500, 723456789, 105, 34);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `itemno` varchar(10) NOT NULL,
  `itemname` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `sellprice` decimal(10,2) NOT NULL,
  `buyprice` decimal(10,2) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`itemno`, `itemname`, `description`, `sellprice`, `buyprice`, `qty`) VALUES
('IT001', 'Paracetamol', 'Pain reliever and fever reducer', 50.00, 30.00, 58),
('IT002', 'Amoxicillin', 'Antibiotic for bacterial infections', 120.00, 90.00, 45),
('IT003', 'Vitamin C', 'Immunity booster supplement', 40.00, 25.00, 197),
('IT004', 'Metformin 500mg', 'Controls blood sugar levels in type 2 diabetes', 50.00, 30.00, 100),
('IT005', 'Amlodipine 5mg', 'For hypertension, relaxes blood vessels', 80.00, 60.00, 75),
('IT006', 'Salbutamol', 'Inhaler for asthma and breathing relief', 450.00, 400.00, 20),
('IT007', 'Loratadine 10mg', 'Allergy relief, non-drowsy antihistamine', 60.00, 45.00, 150),
('IT008', 'Sumatriptan 50mg', 'Relieves migraines and cluster headaches', 300.00, 250.00, 28),
('IT009', 'Ibuprofen 400mg', 'Pain reliever and anti-inflammatory', 25.00, 15.00, 300),
('IT010', 'Ferrous Sulfate', 'Treats iron deficiency anemia', 35.00, 20.00, 100),
('IT011', 'Naproxen 500mg', 'Reduces pain, inflammation, and stiffness', 90.00, 70.00, 60),
('IT012', 'Paracetamol', 'Pain reliever and fever reducer', 50.00, 30.00, 100),
('IT013', 'Vitamin C', 'Immunity booster supplement', 40.00, 25.00, 200);

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `patientno` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` int(20) DEFAULT NULL,
  `address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`patientno`, `name`, `phone`, `address`) VALUES
('PS001', 'Robert Miller', 987654321, '123 Main St, Springfield'),
('PS002', 'Olivia Taylor', 876543210, '456 Elm St, Riverside'),
('PS003', 'Daniel Wilson', 765432109, '789 Oak Ave, Lakeside'),
('PS004', 'Sophia Anderson', 654321098, '321 Maple St, Greenfield'),
('PS005', 'William Thomas', 543210987, '654 Pine Rd, Hilltown'),
('PS006', 'Mia Martinez', 912345678, '10 Pearl St, Sunnyvale'),
('PS007', 'Benjamin Scott', 822334455, '23 Birch Lane, Riverwood'),
('PS008', 'Isabella Moore', 735556673, '45 Cedar Blvd, Oceanview');

-- --------------------------------------------------------

--
-- Table structure for table `prescription`
--

CREATE TABLE `prescription` (
  `presno` varchar(255) NOT NULL,
  `channelid` varchar(255) NOT NULL,
  `doctorname` varchar(255) NOT NULL,
  `diseasetype` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `prescription`
--

INSERT INTO `prescription` (`presno`, `channelid`, `doctorname`, `diseasetype`, `description`) VALUES
('PC001', 'C012', 'D001', 'Flu', 'Prescribed paracetamol 500mg for fever and pain relief.'),
('PC002', 'C001', 'D001', 'Diabetes', 'Prescribed metformin 500mg, twice daily.'),
('PC003', 'C005', 'D001', 'Hypertension', 'Prescribed amlodipine 5mg, once daily.'),
('PC004', 'C009', 'D001', 'Asthma', 'Prescribed inhaler with salbutamol for symptom relief.'),
('PC005', 'C013', 'D002', 'Allergy', 'Prescribed loratadine 10mg, once daily.'),
('PC006', 'C019', 'D002', 'Migraine', 'Prescribed sumatriptan 50mg, as needed for headaches.');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `subtotal` int(11) NOT NULL,
  `pay` int(11) NOT NULL,
  `balance` int(11) NOT NULL,
  `prescriptionNo` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`id`, `date`, `subtotal`, `pay`, `balance`, `prescriptionNo`) VALUES
(17, '2024-12-25', 100, 200, 100, 'PC001'),
(18, '2025-01-02', 460, 500, 40, 'PC002'),
(19, '2025-01-02', 600, 1000, 400, 'PC006'),
(20, '2025-01-02', 220, 1000, 780, 'PC003');

-- --------------------------------------------------------

--
-- Table structure for table `salesproduct`
--

CREATE TABLE `salesproduct` (
  `id` int(11) NOT NULL,
  `sales_id` int(11) NOT NULL,
  `product_id` varchar(255) NOT NULL,
  `sellprice` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `salesproduct`
--

INSERT INTO `salesproduct` (`id`, `sales_id`, `product_id`, `sellprice`, `qty`, `total`) VALUES
(19, 17, 'IT001', 50, 2, 100),
(20, 18, 'IT002', 120, 3, 360),
(21, 18, 'IT001', 50, 2, 100),
(22, 19, 'IT008', 300, 2, 600),
(23, 20, 'IT001', 50, 2, 100),
(24, 20, 'IT003', 40, 3, 120);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `utype` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `username`, `password`, `utype`) VALUES
(19, 'person 1', '1', '1', 'Pharmacist'),
(20, 'person 2', '2', '2', 'Doctor'),
(21, 'person 3', '3', '3', 'Receptionist'),
(22, 'person 4', '4', '4', 'Admin'),
(23, 'Dr. John Doe ', 'John_Doe', '5', 'Doctor'),
(31, 'Dr. Jane Smith', 'Jane_Smith', '6', 'Doctor'),
(32, 'Dr. Alan Brown', 'Alan_Brown', '7', 'Doctor'),
(33, 'Dr. Emily White', 'Emily_White', 'Emily_White123', 'Doctor'),
(34, 'Dr. Michael Green', 'Michael_Green', 'Michael_Green123', 'Doctor');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `channel`
--
ALTER TABLE `channel`
  ADD PRIMARY KEY (`channel_no`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`doctorno`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`itemno`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`patientno`);

--
-- Indexes for table `prescription`
--
ALTER TABLE `prescription`
  ADD PRIMARY KEY (`presno`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `salesproduct`
--
ALTER TABLE `salesproduct`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `salesproduct`
--
ALTER TABLE `salesproduct`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
