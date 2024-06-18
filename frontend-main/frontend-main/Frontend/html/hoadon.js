document.addEventListener('DOMContentLoaded', function() {
var invoices = [
    { id: 1, invoiceDate: '2023-01-01', room: '101', electricity: 200000, water: 100000, roomPrice: 1500000, finalPayment: 1800000 },
    { id: 2, invoiceDate: '2023-02-01', room: '102', electricity: 300000, water: 150000, roomPrice: 2000000, finalPayment: 2450000 },
    { id: 3, invoiceDate: '2022-03-01', room: '103', electricity: 250000, water: 120000, roomPrice: 1800000, finalPayment: 2170000 },
    { id: 4, invoiceDate: '2023-04-01', room: '101', electricity: 280000, water: 110000, roomPrice: 1900000, finalPayment: 2290000 },
    { id: 5, invoiceDate: '2023-05-01', room: '105', electricity: 270000, water: 130000, roomPrice: 1950000, finalPayment: 2350000 },
    { id: 6, invoiceDate: '2022-06-01', room: '102', electricity: 290000, water: 140000, roomPrice: 2100000, finalPayment: 2440000 },
    // Thêm các hóa đơn khác tương tự ở đây...
];

    // Function to load and display filtered invoices
    function loadInvoiceList() {
        var searchYear = document.getElementById('searchInputYear').value.trim();
        var searchMonth = document.getElementById('searchInputMonth').value.trim();
        var searchRoom = document.getElementById('searchInputRoom').value.trim().toLowerCase();

        var filteredInvoices = invoices.filter(function(invoice) {
            var matchesYear = searchYear === '' || invoice.invoiceDate.includes(searchYear);
            var matchesMonth = searchMonth === 'all' || invoice.invoiceDate.substring(5, 7) === searchMonth;
            var matchesRoom = searchRoom === '' || invoice.room.toLowerCase().includes(searchRoom);
            return matchesYear && matchesMonth && matchesRoom;
        });

        var invoiceListDiv = document.getElementById('invoiceList');
        invoiceListDiv.innerHTML = '';

        filteredInvoices.forEach(function(invoice) {
            var invoiceDiv = document.createElement('div');
            invoiceDiv.className = 'col-md-4 invoice-grid';
            invoiceDiv.innerHTML = `
                <div class="room-product-main">
                    <div class="room-product-bottom">
                        <h4><a href="#">Ngày lập: ${invoice.invoiceDate}</a></h4>
                        <p>Số phòng: ${invoice.room}</p>
                        <p>Tiền điện: ${invoice.electricity.toLocaleString()} VNĐ</p>
                        <p>Tiền nước: ${invoice.water.toLocaleString()} VNĐ</p>
                        <p>Đơn giá phòng: ${invoice.roomPrice.toLocaleString()} VNĐ</p>
                        <p>Tổng thanh toán: ${invoice.finalPayment.toLocaleString()} VNĐ</p>
                        <button class="btn btn-primary btn-sm edit-btn" data-id="${invoice.id}">Sửa</button>
                        <button class="btn btn-danger btn-sm delete-btn" data-id="${invoice.id}">Xóa</button>
                    </div>
                </div>
            `;
            invoiceListDiv.appendChild(invoiceDiv);
        });
    }

    // Function to calculate total payment based on filtered invoices
    function calculateTotalPayment() {
        var searchYear = document.getElementById('searchInputYear').value.trim();
        var searchMonth = document.getElementById('searchInputMonth').value.trim();
        var searchRoom = document.getElementById('searchInputRoom').value.trim().toLowerCase();

        var filteredInvoices = invoices.filter(function(invoice) {
            var matchesYear = searchYear === '' || invoice.invoiceDate.includes(searchYear);
            var matchesMonth = searchMonth === 'all' || invoice.invoiceDate.substring(5, 7) === searchMonth;
            var matchesRoom = searchRoom === '' || invoice.room.toLowerCase().includes(searchRoom);
            return matchesYear && matchesMonth && matchesRoom;
        });

        var totalPayment = filteredInvoices.reduce(function(acc, invoice) {
            return acc + invoice.finalPayment;
        }, 0);

        var totalPaymentElement = document.getElementById('totalPayment');
        totalPaymentElement.textContent = 'Tổng doanh thu: ' + totalPayment.toLocaleString() + ' VNĐ';
    }

    // Event listeners for search and calculation actions

    document.getElementById('searchYearForm').addEventListener('submit', function(event) {
        event.preventDefault();
        loadInvoiceList();
    });

    document.getElementById('searchInputMonth').addEventListener('change', function() {
        loadInvoiceList();
    });

    document.getElementById('searchRoomForm').addEventListener('submit', function(event) {
        event.preventDefault();
        loadInvoiceList();
    });

    document.getElementById('calculateTotalPaymentButton').addEventListener('click', function() {
        calculateTotalPayment();
    });

    document.getElementById('addInvoiceButton').addEventListener('click', function() {
        $('#addInvoiceModal').modal('show');
        document.getElementById('addInvoiceForm').setAttribute('data-mode', 'add');
        document.getElementById('addInvoiceForm').removeAttribute('data-id');
    });

    document.getElementById('addInvoiceForm').addEventListener('submit', function(event) {
        event.preventDefault();

        var invoiceDate = document.getElementById('invoiceDate').value;
        var room = document.getElementById('invoiceRoom').value;
        var electricity = parseInt(document.getElementById('invoiceElectricity').value) || 0;
        var water = parseInt(document.getElementById('invoiceWater').value) || 0;
        var roomPrice = parseInt(document.getElementById('invoiceRoomPrice').value) || 0;
        var finalPayment = electricity + water + roomPrice;

        var mode = this.getAttribute('data-mode');
        var id = this.getAttribute('data-id');

        if (mode === 'add') {
            var newInvoice = {
                id: invoices.length + 1,
                invoiceDate: invoiceDate,
                room: room,
                electricity: electricity,
                water: water,
                roomPrice: roomPrice,
                finalPayment: finalPayment
            };
            invoices.push(newInvoice);
        } else if (mode === 'edit' && id) {
            var index = invoices.findIndex(function(inv) {
                return inv.id == id;
            });

            if (index !== -1) {
                invoices[index].invoiceDate = invoiceDate;
                invoices[index].room = room;
                invoices[index].electricity = electricity;
                invoices[index].water = water;
                invoices[index].roomPrice = roomPrice;
                invoices[index].finalPayment = finalPayment;
            }
        }

        $('#addInvoiceModal').modal('hide');
        document.getElementById('addInvoiceForm').reset();
        document.getElementById('addInvoiceForm').removeAttribute('data-mode');
        document.getElementById('addInvoiceForm').removeAttribute('data-id');

        loadInvoiceList();
    });

    document.getElementById('invoiceList').addEventListener('click', function(event) {
        if (event.target.classList.contains('edit-btn')) {
            var id = event.target.getAttribute('data-id');
            var invoice = invoices.find(function(inv) {
                return inv.id == id;
            });

            if (invoice) {
                document.getElementById('invoiceDate').value = invoice.invoiceDate;
                document.getElementById('invoiceRoom').value = invoice.room;
                document.getElementById('invoiceElectricity').value = invoice.electricity;
                document.getElementById('invoiceWater').value = invoice.water;
                document.getElementById('invoiceRoomPrice').value = invoice.roomPrice;

                $('#addInvoiceModal').modal('show');
                document.getElementById('addInvoiceForm').setAttribute('data-mode', 'edit');
                document.getElementById('addInvoiceForm').setAttribute('data-id', id);
            }
        }

        if (event.target.classList.contains('delete-btn')) {
            var id = event.target.getAttribute('data-id');
            if (confirm('Bạn có chắc chắn muốn xóa hóa đơn thanh toán này?')) {
                invoices = invoices.filter(function(inv) {
                    return inv.id != id;
                });
                loadInvoiceList();
            }
        }
    });

    // Initially load invoices when the page is loaded
    loadInvoiceList();

});
