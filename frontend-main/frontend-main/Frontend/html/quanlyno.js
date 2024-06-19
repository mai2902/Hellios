document.addEventListener('DOMContentLoaded', async function () {
	//quản lý nợ
	// const invoices = [
	//     { id: 1, month: '2023-01', room: '101', oldDebt: 1000000, newDebt: 200000, finalDebt: 1200000 },
	//     { id: 2, month: '2023-02', room: '102', oldDebt: 500000, newDebt: 300000, finalDebt: 800000 },
	//     { id: 3, month: '2023-03', room: '103', oldDebt: 200000, newDebt: 100000, finalDebt: 300000 },
	//     { id: 4, month: '2023-04', room: '104', oldDebt: 100000, newDebt: 320000, finalDebt: 330000 },
	//     { id: 5, month: '2023-05', room: '105', oldDebt: 500000, newDebt: 200000, finalDebt: 700000 },
	//     { id: 6, month: '2023-06', room: '106', oldDebt: 800000, newDebt: 300000, finalDebt: 1100000 },
	//     { id: 7, month: '2023-07', room: '102', oldDebt: 100000, newDebt: 400000, finalDebt: 500000 },
	//     { id: 8, month: '2023-08', room: '103', oldDebt: 600000, newDebt: 100000, finalDebt: 700000 }
	// ];

	async function getInvoices() {
		let res = await fetch('http://localhost:8000/api/baoCaoNo');
		res = (await res.json()).data;
		res = res.map((v) => {
			return {
				id: v.id,
				month: v.thang,
				room: v.soPhong,
				oldDebt: v.noDau,
				newDebt: v.phatSinh,
				finalDebt: v.noCuoi,
			};
		});
		return res;
	}
	let invoices = await getInvoices();
	// Function to load and display filtered invoices
	function loadInvoiceList() {
		var searchYear = document.getElementById('searchInputYear').value.trim();
		var searchMonth = document.getElementById('searchInputMonth').value.trim();
		var searchRoom = document.getElementById('searchInputRoom').value.trim().toLowerCase();

		var filteredInvoices = invoices.filter(function (invoice) {
			var matchesYear = searchYear === '' || invoice.month.includes(searchYear);
			var matchesMonth = searchMonth === 'all' || invoice.month.endsWith(searchMonth);
			var matchesRoom = searchRoom === '' || invoice.room.toLowerCase().includes(searchRoom);
			return matchesYear && matchesMonth && matchesRoom;
		});

		var invoiceListDiv = document.getElementById('invoiceList');
		invoiceListDiv.innerHTML = '';

		filteredInvoices.forEach(function (invoice) {
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

		var filteredInvoices = invoices.filter(function (invoice) {
			var matchesYear = searchYear === '' || invoice.month.includes(searchYear);
			var matchesMonth = searchMonth === 'all' || invoice.month.endsWith(searchMonth);
			var matchesRoom = searchRoom === '' || invoice.room.toLowerCase().includes(searchRoom);
			return matchesYear && matchesMonth && matchesRoom;
		});

		var totalDebt = filteredInvoices.reduce(function (acc, invoice) {
			return acc + invoice.finalDebt;
		}, 0);

		var totalDebtElement = document.getElementById('totalDebt');
		totalDebtElement.textContent = 'Tổng nợ: ' + totalDebt.toLocaleString() + ' VNĐ';
	}

	// Function to format month for display
	function formatMonth(monthString) {
		var year = new Date(monthString).toISOString().substring(0, 4);
		var month = new Date(monthString).toISOString().substring(5, 7);
		return `${month}/${year}`;
	}

	// Event listeners for search and calculation actions

	document.getElementById('searchYearForm').addEventListener('submit', function (event) {
		event.preventDefault();
		loadInvoiceList();
	});

	document.getElementById('searchInputMonth').addEventListener('change', function () {
		loadInvoiceList();
	});

	document.getElementById('searchRoomForm').addEventListener('submit', function (event) {
		event.preventDefault();
		loadInvoiceList();
	});

	document.getElementById('calculateTotalDebtButton').addEventListener('click', function () {
		calculateTotalDebt();
	});

	document.getElementById('addInvoiceButton').addEventListener('click', function () {
		$('#addInvoiceModal').modal('show');
		document.getElementById('addInvoiceForm').setAttribute('data-mode', 'add');
		document.getElementById('addInvoiceForm').removeAttribute('data-id');
		document.getElementById('modalInvoices').innerHTML = 'Thêm';
		document.getElementById('modalTitleInvoices').innerHTML = 'Thêm hóa đơn nợ mới';
	});

	document.getElementById('addInvoiceForm').addEventListener('submit', async function (event) {
		event.preventDefault();

		var month = document.getElementById('invoiceMonth').value;
		var room = document.getElementById('invoiceRoom').value;
		var oldDebt = parseInt(document.getElementById('invoiceOldDebt').value) || 0;
		var newDebt = parseInt(document.getElementById('invoiceNewDebt').value) || 0;
		var finalDebt = oldDebt + newDebt;

		var mode = this.getAttribute('data-mode');
		var id = this.getAttribute('data-id');

		if (mode === 'add') {
			const newInvoice = {
				thang: new Date(`${month}-01`),
				phong_id: room,
				noDau: oldDebt,
				phatSinh: newDebt,
				noCuoi: finalDebt,
				soPhong: room,
			};

			let res = await fetch('http://localhost:8000/api/baoCaoNo', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(newInvoice),
			});
			res = await res.json();
			if (res.status == 400) {
				alert('Phòng không tồn tại !!!');
			}
			invoices = await getInvoices();
		} else if (mode === 'edit' && id) {
			const newInvoice = {
				thang: new Date(`${month}-01`),
				phong_id: room,
				noDau: oldDebt,
				phatSinh: newDebt,
				noCuoi: finalDebt,
				soPhong: room,
			};
			let res = await fetch(`http://localhost:8000/api/baoCaoNo/${id}`, {
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(newInvoice),
			});
			res = await res.json();
			if (res.status == 400) {
				alert('Phòng không tồn tại !!!');
			}
			invoices = await getInvoices();
		}

		$('#addInvoiceModal').modal('hide');
		document.getElementById('addInvoiceForm').reset();
		document.getElementById('addInvoiceForm').removeAttribute('data-mode');
		document.getElementById('addInvoiceForm').removeAttribute('data-id');

		loadInvoiceList();
	});

	document.getElementById('invoiceList').addEventListener('click', async function (event) {
		if (event.target.classList.contains('edit-btn')) {
			var id = event.target.getAttribute('data-id');
			var invoice = invoices.find(function (inv) {
				return inv.id == id;
			});

			if (invoice) {
				document.getElementById('invoiceMonth').value = invoice.month.substring(0, 7);
				document.getElementById('invoiceRoom').value = invoice.room;
				document.getElementById('invoiceOldDebt').value = invoice.oldDebt;
				document.getElementById('invoiceNewDebt').value = invoice.newDebt;

				$('#addInvoiceModal').modal('show');
				document.getElementById('addInvoiceForm').setAttribute('data-mode', 'edit');
				document.getElementById('addInvoiceForm').setAttribute('data-id', id);
				document.getElementById('modalInvoices').innerHTML = 'Sửa';
				document.getElementById('modalTitleInvoices').innerHTML = 'Sửa hóa đơn nợ mới';
			}
		}

		if (event.target.classList.contains('delete-btn')) {
			var id = event.target.getAttribute('data-id');
			if (confirm('Bạn có chắc chắn muốn xóa hóa đơn nợ này?')) {
				await fetch(`http://localhost:8000/api/baoCaoNo/${id}`, { method: 'DELETE' });
				invoices = await getInvoices();
				loadInvoiceList();
			}
		}
	});

	// Initially load invoices when the page is loaded
	loadInvoiceList();
});
