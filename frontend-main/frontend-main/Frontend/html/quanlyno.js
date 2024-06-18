document.addEventListener('DOMContentLoaded', function() {
    var invoices = [
        { id: 1, month: '2023-01', room: '101', oldDebt: 1000000, newDebt: 200000, finalDebt: 1200000 },
        { id: 2, month: '2023-02', room: '102', oldDebt: 500000, newDebt: 300000, finalDebt: 800000 },
        { id: 3, month: '2021-01', room: '101', oldDebt: 1000000, newDebt: 200000, finalDebt: 1200000 },
        { id: 4, month: '2022-02', room: '103', oldDebt: 100000, newDebt: 3200000, finalDebt: 3300000 }
    ];

    // Function to load and display filtered invoices
    function loadInvoiceList() {
        var searchYear = document.getElementById('searchInputYear').value.trim();
        var searchMonth = document.getElementById('searchInputMonth').value.trim();
        var searchRoom = document.getElementById('searchInputRoom').value.trim().toLowerCase();

        var filteredInvoices = invoices.filter(function(invoice) {
            var matchesYear = searchYear === '' || invoice.month.includes(searchYear);
            var matchesMonth = searchMonth === 'all' || invoice.month.endsWith(searchMonth);
            var matchesRoom = searchRoom === '' || invoice.room.toLowerCase().includes(searchRoom);
            return matchesYear && matchesMonth && matchesRoom;
        });

        var invoiceListDiv = document.getElementById('invoiceList');
        invoiceListDiv.innerHTML = '';

        filteredInvoices.forEach(function(invoice) {
            var invoiceDiv = document.createElement('div');
            invoiceDiv.className = 'col-md-3 invoice-grid';
            invoiceDiv.innerHTML = `
                <div class="room-product-main">
                    <div class="room-product-bottom">
                        <h4><a href="#">Tháng năm: ${formatMonth(invoice.month)}</a></h4>
                        <p>Số phòng: ${invoice.room}</p>
                        <p>Nợ đầu: ${invoice.oldDebt.toLocaleString()} VNĐ</p>
                        <p>Phát sinh: ${invoice.newDebt.toLocaleString()} VNĐ</p>
                        <p>Nợ cuối: ${invoice.finalDebt.toLocaleString()} VNĐ</p>
                        <button class="btn btn-primary btn-sm edit-btn" data-id="${invoice.id}">Sửa</button>
                        <button class="btn btn-danger btn-sm delete-btn" data-id="${invoice.id}">Xóa</button>
                    </div>
                </div>
            `;
            invoiceListDiv.appendChild(invoiceDiv);
        });
    }

    // Function to calculate total debt based on filtered invoices
    function calculateTotalDebt() {
        var searchYear = document.getElementById('searchInputYear').value.trim();
        var searchMonth = document.getElementById('searchInputMonth').value.trim();
        var searchRoom = document.getElementById('searchInputRoom').value.trim().toLowerCase();

        var filteredInvoices = invoices.filter(function(invoice) {
            var matchesYear = searchYear === '' || invoice.month.includes(searchYear);
            var matchesMonth = searchMonth === 'all' || invoice.month.endsWith(searchMonth);
            var matchesRoom = searchRoom === '' || invoice.room.toLowerCase().includes(searchRoom);
            return matchesYear && matchesMonth && matchesRoom;
        });

        var totalDebt = filteredInvoices.reduce(function(acc, invoice) {
            return acc + invoice.finalDebt;
        }, 0);

        var totalDebtElement = document.getElementById('totalDebt');
        totalDebtElement.textContent = 'Tổng nợ: ' + totalDebt.toLocaleString() + ' VNĐ';
    }

    // Function to format month for display
    function formatMonth(monthString) {
        var year = monthString.substring(0, 4);
        var month = monthString.substring(5);
        return `${month}/${year}`;
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

    document.getElementById('calculateTotalDebtButton').addEventListener('click', function() {
        calculateTotalDebt();
    });

    document.getElementById('addInvoiceButton').addEventListener('click', function() {
        $('#addInvoiceModal').modal('show');
        document.getElementById('addInvoiceForm').setAttribute('data-mode', 'add');
        document.getElementById('addInvoiceForm').removeAttribute('data-id');
    });

    document.getElementById('addInvoiceForm').addEventListener('submit', function(event) {
        event.preventDefault();

        var month = document.getElementById('invoiceMonth').value;
        var room = document.getElementById('invoiceRoom').value;
        var oldDebt = parseInt(document.getElementById('invoiceOldDebt').value) || 0;
        var newDebt = parseInt(document.getElementById('invoiceNewDebt').value) || 0;
        var finalDebt = oldDebt + newDebt;

        var mode = this.getAttribute('data-mode');
        var id = this.getAttribute('data-id');

        if (mode === 'add') {
            var newInvoice = {
                id: invoices.length + 1,
                month: month,
                room: room,
                oldDebt: oldDebt,
                newDebt: newDebt,
                finalDebt: finalDebt
            };
            invoices.push(newInvoice);
        } else if (mode === 'edit' && id) {
            var index = invoices.findIndex(function(inv) {
                return inv.id == id;
            });

            if (index !== -1) {
                invoices[index].month = month;
                invoices[index].room = room;
                invoices[index].oldDebt = oldDebt;
                invoices[index].newDebt = newDebt;
                invoices[index].finalDebt = finalDebt;
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
                document.getElementById('invoiceMonth').value = invoice.month;
                document.getElementById('invoiceRoom').value = invoice.room;
                document.getElementById('invoiceOldDebt').value = invoice.oldDebt;
                document.getElementById('invoiceNewDebt').value = invoice.newDebt;

                $('#addInvoiceModal').modal('show');
                document.getElementById('addInvoiceForm').setAttribute('data-mode', 'edit');
                document.getElementById('addInvoiceForm').setAttribute('data-id', id);
            }
        }

        if (event.target.classList.contains('delete-btn')) {
            var id = event.target.getAttribute('data-id');
            if (confirm('Bạn có chắc chắn muốn xóa hóa đơn nợ này?')) {
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
