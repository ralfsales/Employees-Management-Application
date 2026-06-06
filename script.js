let employees = [
  {
    id: 1,
    name: "Rafael Sales",
    role: "Manager",
    department: "Projects",
    email: "rafael.sales@example.com"
  },
  {
    id: 2,
    name: "Ana Martins",
    role: "Analyst",
    department: "HR",
    email: "ana.martins@example.com"
  },
  {
    id: 3,
    name: "Lucas Pereira",
    role: "Developer",
    department: "Development",
    email: "lucas.pereira@example.com"
  },
  {
    id: 4,
    name: "Sofia Almeida",
    role: "Supervisor",
    department: "Sales",
    email: "sofia.almeida@example.com"
  },
  {
    id: 5,
    name: "Daniel Costa",
    role: "Technician",
    department: "IT Support",
    email: "daniel.costa@example.com"
  },
  {
    id: 6,
    name: "Mariana Silva",
    role: "Developer",
    department: "Development",
    email: "mariana.silva@example.com"
  }
];

let editingEmployeeId = null;

const employeeList = document.getElementById("employeeList");
const searchInput = document.getElementById("searchInput");
const departmentFilter = document.getElementById("departmentFilter");
const roleFilter = document.getElementById("roleFilter");
const sortOption = document.getElementById("sortOption");

function renderEmployees() {
  let filteredEmployees = [...employees];

  const searchText = searchInput.value.toLowerCase();
  const selectedDepartment = departmentFilter.value;
  const selectedRole = roleFilter.value;
  const sortBy = sortOption.value;

  filteredEmployees = filteredEmployees.filter(employee => {
    const matchesSearch =
      employee.name.toLowerCase().includes(searchText) ||
      employee.role.toLowerCase().includes(searchText) ||
      employee.department.toLowerCase().includes(searchText) ||
      employee.email.toLowerCase().includes(searchText);

    const matchesDepartment =
      selectedDepartment === "" || employee.department === selectedDepartment;

    const matchesRole =
      selectedRole === "" || employee.role === selectedRole;

    return matchesSearch && matchesDepartment && matchesRole;
  });

  filteredEmployees.sort((a, b) => {
    return a[sortBy].localeCompare(b[sortBy]);
  });

  employeeList.innerHTML = "";

  if (filteredEmployees.length === 0) {
    employeeList.innerHTML = "<p>No employees found.</p>";
    return;
  }

  filteredEmployees.forEach(employee => {
    const card = document.createElement("div");
    card.className = "employee-card";

    card.innerHTML = `
      <strong>${employee.name}</strong>
      <span>${employee.role}</span>
      <span>${employee.department}</span>
      <span>${employee.email}</span>
      <div class="actions">
        <button class="edit" onclick="editEmployee(${employee.id})">Edit</button>
        <button class="delete" onclick="deleteEmployee(${employee.id})">Delete</button>
      </div>
    `;

    employeeList.appendChild(card);
  });

  updateStats();
}

function saveEmployee() {
  const name = document.getElementById("employeeName").value.trim();
  const role = document.getElementById("employeeRole").value;
  const department = document.getElementById("employeeDepartment").value;
  const email = document.getElementById("employeeEmail").value.trim();

  if (!name || !role || !department || !email) {
    alert("Please fill in all fields.");
    return;
  }

  if (editingEmployeeId) {
    employees = employees.map(employee => {
      if (employee.id === editingEmployeeId) {
        return { id: editingEmployeeId, name, role, department, email };
      }
      return employee;
    });

    editingEmployeeId = null;
    document.getElementById("saveButton").textContent = "Add Employee";
    document.getElementById("formTitle").textContent = "Add New Employee";
  } else {
    const newEmployee = {
      id: Date.now(),
      name,
      role,
      department,
      email
    };

    employees.push(newEmployee);
  }

  resetForm();
  renderEmployees();
}

function editEmployee(id) {
  const employee = employees.find(employee => employee.id === id);

  document.getElementById("employeeName").value = employee.name;
  document.getElementById("employeeRole").value = employee.role;
  document.getElementById("employeeDepartment").value = employee.department;
  document.getElementById("employeeEmail").value = employee.email;

  editingEmployeeId = id;

  document.getElementById("saveButton").textContent = "Update Employee";
  document.getElementById("formTitle").textContent = "Edit Employee";
}

function deleteEmployee(id) {
  const confirmDelete = confirm("Are you sure you want to delete this employee?");

  if (confirmDelete) {
    employees = employees.filter(employee => employee.id !== id);
    renderEmployees();
  }
}

function resetForm() {
  document.getElementById("employeeName").value = "";
  document.getElementById("employeeRole").value = "";
  document.getElementById("employeeDepartment").value = "";
  document.getElementById("employeeEmail").value = "";

  editingEmployeeId = null;
  document.getElementById("saveButton").textContent = "Add Employee";
  document.getElementById("formTitle").textContent = "Add New Employee";
}

function updateStats() {
  document.getElementById("totalEmployees").textContent = employees.length;

  const departments = new Set(employees.map(employee => employee.department));
  document.getElementById("totalDepartments").textContent = departments.size;

  const managers = employees.filter(employee => employee.role === "Manager");
  document.getElementById("totalManagers").textContent = managers.length;
}

searchInput.addEventListener("input", renderEmployees);
departmentFilter.addEventListener("change", renderEmployees);
roleFilter.addEventListener("change", renderEmployees);
sortOption.addEventListener("change", renderEmployees);

renderEmployees();
