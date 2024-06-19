// Dữ liệu phòng ban đầu
// const rooms = [
//     { id: 1, name: 'Phòng 111', type: 'A', status: 'available', price: 2000000 },
//     { id: 2, name: 'Phòng 112', type: 'B', status: 'available', price: 2500000 },
//     { id: 3, name: 'Phòng 113', type: 'C', status: 'available', price: 3000000 },
//     { id: 4, name: 'Phòng 114', type: 'D', status: 'available', price: 3500000 },
//     { id: 5, name: 'Phòng 101', type: 'D', status: 'available', price: 3500000 },
//     { id: 6, name: 'Phòng 102', type: 'C', status: 'occupied', price: 3000000 },
//     { id: 7, name: 'Phòng 115', type: 'B', status: 'available', price: 2500000 },
//     { id: 8, name: 'Phòng 106', type: 'D', status: 'occupied', price: 2000000 },
//     { id: 9, name: 'Phòng 103', type: 'A', status: 'occupied', price: 2000000 },
//     { id: 10, name: 'Phòng 104', type: 'B', status: 'occupied', price: 2500000 },
//     { id: 11, name: 'Phòng 105', type: 'C', status: 'occupied', price: 3000000 },
// ];
function getPrice(type) {
	if (type === 'A') {
		return '200,000 VNĐ';
	}
	if (type === 'B') {
		return '250,000 VNĐ';
	}
	if (type === 'C') {
		return '300,000 VNĐ';
	}
	if (type === 'D') {
		return '200,000 VNĐ';
	}
}
async function getRoom() {
	let res = await fetch('http://localhost:8000/api/phong');
	res = (await res.json()).data;
	res = res.map((v) => {
		return {
			id: v.id,
			name: `Phòng ${v.soPhong}`,
			type: v.loaiPhong,
			status: v.tinhTrang,
			price: getPrice(v.loaiPhong),
		};
	});
	return res;
}

document.addEventListener('DOMContentLoaded', async function () {
	let rooms = await getRoom();
	// Bắt sự kiện click vào nút "Thêm phòng"
	document.getElementById('addRoomButton').addEventListener('click', function () {
		$('#addRoomModal').modal('show'); // Hiển thị modal
		document.getElementById('addRoomForm').setAttribute('data-mode', 'add'); // Đặt chế độ thêm mới
		document.getElementById('addRoomForm').removeAttribute('data-index'); // Xóa chỉ số chỉ mục
        document.getElementById('modal-title').innerHTML = 'Thêm phòng mới';
        document.getElementById('modalRoomButton').innerHTML = 'Thêm';
	});

	// Xử lý sự kiện submit form "Thêm phòng" hoặc "Sửa phòng"
	document.getElementById('addRoomForm').addEventListener('submit', async function (event) {
		event.preventDefault(); // Ngăn chặn submit form mặc định

		// Lấy giá trị từ form
		var roomNumber = document.getElementById('roomNumber').value;
		var roomType = document.getElementById('roomType').value;

		// Lấy chế độ của form (thêm mới hoặc sửa)
		var mode = this.getAttribute('data-mode');
		var index = this.getAttribute('data-index');

		if (mode === 'add') {
			// Tạo đối tượng phòng mới
			const newRoom = {
				id: rooms.length + 1,
				soPhong: roomNumber,
				loaiPhong: roomType,
				tinhTrang: 'available',
			};
			await fetch('http://localhost:8000/api/phong', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(newRoom),
			});
			rooms = await getRoom();
		} else if (mode === 'edit' && index !== null) {
			// Cập nhật thông tin phòng đã có
            let room = {
				id: rooms[index].id,
				soPhong: roomNumber,
				loaiPhong: roomType,
				tinhTrang: rooms[index].status,
			};
            await fetch(`http://localhost:8000/api/phong/${rooms[index].id}`, {
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(room),
			});
            rooms = await getRoom();
		}

		// Đóng modal sau khi thêm hoặc sửa thành công
		$('#addRoomModal').modal('hide');

		// Đặt lại form về trạng thái ban đầu
		document.getElementById('addRoomForm').reset();
		document.getElementById('addRoomForm').removeAttribute('data-mode');
		document.getElementById('addRoomForm').removeAttribute('data-index');

		// Cập nhật và hiển thị danh sách phòng mới
		filterRooms();
	});

	// Hiển thị danh sách phòng khi tải trang lần đầu
	filterRooms();

	// Hàm lọc và hiển thị danh sách phòng
	function filterRooms() {
		const searchTerm = document.getElementById('searchInput').value.toLowerCase();
		const roomType = document.getElementById('roomTypeFilter').value;
		const roomStatus = document.getElementById('roomStatusFilter').value;

		const filteredRooms = rooms.filter((room) => {
			const matchesSearch = room.name.toLowerCase().includes(searchTerm) || room.type.toLowerCase().includes(searchTerm) || room.status.toLowerCase().includes(searchTerm);
			const matchesType = roomType === 'all' || room.type === roomType;
			const matchesStatus = roomStatus === 'all' || room.status === roomStatus;

			return matchesSearch && matchesType && matchesStatus;
		});

		// Sắp xếp lại danh sách phòng theo số phòng trong tên
		const sortedRooms = sortRoomsByName(filteredRooms);

		// Hiển thị danh sách phòng đã lọc và sắp xếp
		displayRooms(sortedRooms);
	}

	// Hàm sắp xếp danh sách phòng theo số phòng trong tên
	function sortRoomsByName(rooms) {
		return rooms.sort((a, b) => {
			const numberA = extractRoomNumber(a.name);
			const numberB = extractRoomNumber(b.name);
			return numberA - numberB;
		});
	}

	// Hàm trích xuất số phòng từ tên phòng
	function extractRoomNumber(roomName) {
		const matches = roomName.match(/\d+/);
		return matches ? parseInt(matches[0]) : Infinity;
	}

	// Hàm hiển thị danh sách phòng
	function displayRooms(filteredRooms) {
		const roomList = document.getElementById('roomList');
		roomList.innerHTML = '';

		filteredRooms.forEach((room, index) => {
			const roomDiv = document.createElement('div');
			roomDiv.className = 'col-md-4 room-grid';
			roomDiv.innerHTML = `
                <div class="room-product-main">
                    <div class="room-product-bottom">
                        <h3><a href="#?id=${room.id}">${room.name}</a></h3>
                        <p>Loại: ${room.type}</p>
                        <p>Giá: ${room.price}</p>
                        <p>Tình trạng: ${room.status === 'available' ? 'Còn trống' : 'Không còn trống'}</p>
                        <button class="btn btn-danger delete-room" data-index="${index}">Xóa</button>
                        <button class="btn btn-primary edit-room" style="margin-left: 10px;" data-index="${index}">Sửa</button>
                    </div>
                </div>
            `;
			roomList.appendChild(roomDiv);
		});

		// Thêm sự kiện xóa và sửa cho từng phòng
		addDeleteEventListeners();
		addEditEventListeners();
	}

	// Đảm bảo filterRooms() được gọi khi các giá trị bộ lọc thay đổi hoặc khi người dùng tìm kiếm
	document.getElementById('searchForm').addEventListener('submit', function (event) {
		event.preventDefault();
		filterRooms();
	});

	document.getElementById('roomTypeFilter').addEventListener('change', filterRooms);
	document.getElementById('roomStatusFilter').addEventListener('change', filterRooms);

	// Hàm xử lý sự kiện xóa phòng
	function addDeleteEventListeners() {
		const deleteButtons = document.querySelectorAll('.delete-room');
		deleteButtons.forEach((button) => {
			button.addEventListener('click', async function () {
				const index = parseInt(this.getAttribute('data-index'));
				const roomName = rooms[index].name;
				const roomId = rooms[index].id;
				// Xác nhận xóa phòng
				if (confirm(`Bạn chắc chắn muốn xóa '${roomName}'?`)) {
					await fetch(`http://localhost:8000/api/phong/${roomId}`, { method: 'DELETE' });
					rooms = await getRoom();
					filterRooms(); // Cập nhật lại danh sách phòng sau khi xóa
				}
			});
		});
	}

	// Hàm xử lý sự kiện sửa phòng
	function addEditEventListeners() {
		const editButtons = document.querySelectorAll('.edit-room');
		editButtons.forEach((button) => {
			button.addEventListener('click', function () {
				const index = parseInt(this.getAttribute('data-index'));
				const room = rooms[index];

				// Hiển thị modal chỉnh sửa phòng và điền thông tin phòng vào form
				$('#addRoomModal').modal('show');
				document.getElementById('addRoomForm').setAttribute('data-mode', 'edit');
				document.getElementById('addRoomForm').setAttribute('data-index', index);

				// Điền thông tin phòng vào form
				document.getElementById('roomNumber').value = extractRoomNumber(room.name);
				document.getElementById('roomType').value = room.type;
                document.getElementById('modal-title').innerHTML = 'Sửa thông tin phòng';
                document.getElementById('modalRoomButton').innerHTML = 'Sửa';
			});
		});
	}

	filterRooms();
});
