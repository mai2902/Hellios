document.addEventListener('DOMContentLoaded', function() {
    // Bắt sự kiện click vào nút "Thêm phòng"
    document.getElementById('addRoomButton').addEventListener('click', function() {
        $('#addRoomModal').modal('show'); // Hiển thị modal
        document.getElementById('addRoomForm').setAttribute('data-mode', 'add'); // Đặt chế độ thêm mới
        document.getElementById('addRoomForm').setAttribute('data-index', ''); // Đặt chỉ số chỉ mục rỗng
    });

    // Xử lý sự kiện submit form "Thêm phòng" hoặc "Sửa phòng"
    document.getElementById('addRoomForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Ngăn chặn submit form mặc định

        // Lấy giá trị từ form
        var roomNumber = document.getElementById('roomNumber').value;
        var roomType = document.getElementById('roomType').value;
        var roomStatus = document.getElementById('roomStatus').value;

        // Lấy chế độ của form (thêm mới hoặc sửa)
        var mode = this.getAttribute('data-mode');
        var index = parseInt(this.getAttribute('data-index'));

        if (mode === 'add') {
            // Tạo đối tượng phòng mới với các thuộc tính giống như các phòng hiện có
            var newRoom = {
                id: rooms.length + 1, // Tạo id mới dựa trên số lượng phòng hiện có
                name: 'Phòng ' + roomNumber,
                type: roomType,
                status: roomStatus,
                price: '$200' // Giả định giá phòng cố định cho tất cả các phòng mới
            };

            // Thêm phòng mới vào mảng rooms
            rooms.push(newRoom);
        } else if (mode === 'edit' && index >= 0) {
            // Cập nhật thông tin phòng đã có
            rooms[index].name = 'Phòng ' + roomNumber;
            rooms[index].type = roomType;
            rooms[index].status = roomStatus;
        }

        // Đóng modal sau khi thêm hoặc sửa thành công
        $('#addRoomModal').modal('hide');

        // Đặt lại form về trạng thái ban đầu
        document.getElementById('addRoomForm').reset();
        document.getElementById('addRoomForm').removeAttribute('data-mode');
        document.getElementById('addRoomForm').removeAttribute('data-index');

        // Cập nhật danh sách phòng hiển thị
        filterRooms(); // Gọi lại hàm filterRooms để cập nhật danh sách phòng
    });

    // Hiển thị tất cả các phòng đã sắp xếp theo số phòng trong tên khi tải lần đầu
    const initialSortedRooms = sortRoomsByName(rooms);
    displayRooms(initialSortedRooms);
});

// Hàm sắp xếp các phòng theo số phòng trong tên
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

// Hàm hiển thị các phòng
function displayRooms(filteredRooms) {
    const roomList = document.getElementById('roomList');
    roomList.innerHTML = '';
    filteredRooms.forEach((room, index) => {
        const roomDiv = document.createElement('div');
        roomDiv.className = 'col-md-4 room-grid';
        roomDiv.innerHTML = `
            <div class="room-product-main">
                <div class="room-product-bottom">
                    <h3><a href="single.html?id=${room.id}">${room.name}</a></h3>
                    <p>Loại: ${room.type}</p>
                    <p>Giá: ${room.price}</p>
                    <p>Tình trạng: ${room.status === 'available' ? 'Còn trống' : 'Không còn trống'}</p>
                    <button class="btn btn-danger delete-room" style="background-color: #f44336;" data-index="${index}">Xóa</button>
                    <button class="btn btn-primary edit-room" style="margin-left: 10px;" data-index="${index}">Sửa</button>
                </div>
            </div>
        `;
        roomList.appendChild(roomDiv);
    });
    addDeleteEventListeners();
    addEditEventListeners();
}

function addDeleteEventListeners() {
    const deleteButtons = document.querySelectorAll('.delete-room');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const index = this.getAttribute('data-index');
            rooms.splice(index, 1);
            filterRooms();
        });
    });
}

function addEditEventListeners() {
    const editButtons = document.querySelectorAll('.edit-room');
    editButtons.forEach(button => {
        button.addEventListener('click', function() {
            const index = this.getAttribute('data-index');
            const room = rooms[index];

            // Hiển thị modal chỉnh sửa phòng và điền thông tin phòng vào form
            $('#addRoomModal').modal('show');
            document.getElementById('addRoomForm').setAttribute('data-mode', 'edit');
            document.getElementById('addRoomForm').setAttribute('data-index', index);

            // Điền thông tin phòng vào form
            document.getElementById('roomNumber').value = extractRoomNumber(room.name);
            document.getElementById('roomType').value = room.type;
            document.getElementById('roomStatus').value = room.status;
        });
    });
}

function filterRooms() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    const roomType = document.getElementById('roomTypeFilter').value;
    const roomStatus = document.getElementById('roomStatusFilter').value;

    const filteredRooms = rooms.filter(room => {
        const matchesSearch = room.name.toLowerCase().includes(searchTerm) || 
                              room.type.toLowerCase().includes(searchTerm) || 
                              room.status.toLowerCase().includes(searchTerm);
        const matchesType = roomType === 'all' || room.type === roomType;
        const matchesStatus = roomStatus === 'all' || room.status === roomStatus;

        return matchesSearch && matchesType && matchesStatus;
    });

    // Sắp xếp lại filteredRooms theo số phòng trong tên trước khi hiển thị
    const sortedRooms = sortRoomsByName(filteredRooms);

    displayRooms(sortedRooms);
}

document.getElementById('searchForm').addEventListener('submit', function(event) {
    event.preventDefault();
    filterRooms();
});

document.getElementById('roomTypeFilter').addEventListener('change', filterRooms);
document.getElementById('roomStatusFilter').addEventListener('change', filterRooms);

// Dữ liệu phòng ban đầu
const rooms = [
    { id: 1, name: 'Phòng 1', type: 'A', status: 'available', price: '$200' },
    { id: 2, name: 'Phòng 2', type: 'B', status: 'occupied', price: '$200' },
    { id: 3, name: 'Phòng 3', type: 'C', status: 'available', price: '$200' },
    { id: 4, name: 'Phòng 4', type: 'D', status: 'occupied', price: '$200' },
    { id: 5, name: 'Phòng 10', type: 'D', status: 'available', price: '$200' },
    { id: 6, name: 'Phòng 11', type: 'C', status: 'occupied', price: '$200' },
    { id: 7, name: 'Phòng 14', type: 'B', status: 'available', price: '$200' },
    { id: 8, name: 'Phòng 12', type: 'A', status: 'occupied', price: '$200' },
    // Add more rooms as needed
];
