CREATE TABLE nguoiThue (
	id int PRIMARY KEY,
	hoten varchar(255),
	diachi varchar(255),
	ngaysinh date,
	cccd varchar(255),
	sdt varchar(255)
)


CREATE TABLE phieuThue (
	id int,
	nguoithue_id int,
	ngaybatdau date,
	ngayketthuc date,
	phong_id int PRIMARY KEY
)

CREATE TABLE phong (
	id int PRIMARY KEY,
	sophong int,
	loaiphong varchar(255),
	tinhtrang varchar(255)
)

CREATE TABLE hoaDon (
	id int,
	phong_id int,
	tiendien bigint,
	tiennuoc bigint,
	dongia bigint,
	tongtien bigint,
	phatsinh bigint,
	ngaylaphoadon date
)

CREATE TABLE baoCaoNo (
	id int,
	phong_id int PRIMARY KEY,
	nodau bigint,
	nocuoi bigint,
	thang date
)

CREATE TABLE loaiPhong (
	id int,
	loai varchar(255) PRIMARY KEY,
	dongia bigint
)


ALTER TABLE phieuThue 
ADD FOREIGN KEY (nguoithue_id) REFERENCES nguoiThue(id);

ALTER TABLE hoaDon 
ADD FOREIGN KEY (phong_id) REFERENCES phong(id);
