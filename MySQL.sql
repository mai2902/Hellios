CREATE TABLE nguoiThue (
	id int AUTO_INCREMENT PRIMARY KEY,
	hoten varchar(255),
	diachi varchar(255),
	ngaysinh date,
	cccd varchar(255),
	sdt varchar(255)
)


CREATE TABLE phieuThue (
	id int AUTO_INCREMENT,
	nguoithue_id int,
	ngaybatdau date,
	ngayketthuc date,
	phong_id int PRIMARY KEY
)

CREATE TABLE phong (
	id int AUTO_INCREMENT PRIMARY KEY,
	sophong int,
	loaiphong varchar(255),
	tinhtrang varchar(255)
)

CREATE TABLE hoaDon (
	id int AUTO_INCREMENT,
	phong_id int,
	tiendien bigint,
	tiennuoc bigint,
	dongia bigint,
	tongtien bigint,
	ngaylaphoadon date
)

CREATE TABLE baoCaoNo (
	id int AUTO_INCREMENT,
	phong_id int PRIMARY KEY,
	nodau bigint,
	nocuoi bigint,
	phatsinh bigint,
	thang date
)

CREATE TABLE loaiPhong (
	id int AUTO_INCREMENT,
	loai varchar(255) PRIMARY KEY,
	dongia bigint
)


ALTER TABLE phieuThue 
ADD FOREIGN KEY (nguoithue_id) REFERENCES nguoiThue(id);

ALTER TABLE hoaDon 
ADD FOREIGN KEY (phong_id) REFERENCES phong(id);
