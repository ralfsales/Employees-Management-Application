let employees = [
  {
    id: 1,
    name: "Ana Martins",
    role: "Analyst",
    department: "HR",
    level: "Junior"
  },
  {
    id: 2,
    name: "Lucas Pereira",
    role: "Developer",
    department: "Development",
    level: "Mid-Level"
  },
  {
    id: 3,
    name: "Sofia Almeida",
    role: "Supervisor",
    department: "Sales",
    level: "Senior"
  },
  {
    id: 4,
    name: "Daniel Costa",
    role: "Technician",
    department: "IT Support",
    level: "Junior"
  },
  {
    id: 5,
    name: "Mariana Silva",
    role: "Manager",
    department: "Projects",
    level: "Senior"
  }
];

let editingEmployeeId = null;

function saveEmployee() {
  const name = document.getElementById("employeeName").value.trim();
  const role = document.getElementById("employeeRole").value;
  const department = document.getElementById("employeeDepartment").value;
  const level = document.getElementById("employeeLevel").value;

  if (!name || !role || !department || !level) {
    alert("Please fill in all fields.");
    return;
  }

  if (editingEmployeeId) {
    employees = employees.map(employee => {
      if (employee.id === editingEmployeeId) {
        return { id: editingEmployeeId, name, role, department, level };
      }
      return employee;
    });

    alert("Employee updated successfully.");
  } else {
    employees.push({
      id: Date.now(),
      name,
      role,
      department,
      level
    });

    alert("Employee added successfully.");
  }

  resetForm();
  clearSearch();
}

function searchEmployees() {
  const searchText = document.getElementById("searchInput").value.toLowerCase();
  const department = document.getElementById("departmentFilter").value;
  const role = document.getElementById("roleFilter").value;
  const level = document.getElementById("levelFilter").value;

  const results = employees.filter(employee => {
    const matchesName =
      searchText === "" || employee.name.toLowerCase().includes(searchText);

    const matchesDepartment =
      department === "" || employee.department === department;

    const matchesRole =
      role === "" || employee.role === role;

    const matchesLevel =
      level === "" || employee.level === level;

    return matchesName && matchesDepartment && matchesRole && matchesLevel;
  });

  renderResults(results);
}

function renderResults(results) {
  const resultsSection = document.getElementById("resultsSection");
  const employeeList = document.getElementById("employeeList");

  resultsSection.classList.remove("hidden");
  employeeList.innerHTML = "";

  if (results.length === 0) {
    employeeList.innerHTML = "<p>No employee records found.</p>";
    return;
  }

  results.forEach(employee => {
    const card = document.createElement("div");
    card.className = "employee-card";

    card.innerHTML = `
      <strong>${employee.name}</strong>
      <span>${employee.role}</span>
      <span>${employee.department}</span>
      <span>${employee.level}</span>
      <div class="actions">
        <button class="edit" onclick="editEmployee(${employee.id})">Edit</button>
        <button class="delete" onclick="deleteEmployee(${employee.id})">Delete</button>
      </div>
    `;

    employeeList.appendChild(card);
  });
}

function editEmployee(id) {
  const employee = employees.find(employee => employee.id === id);

  document.getElementById("employeeName").value = employee.name;
  document.getElementById("employeeRole").value = employee.role;
  document.getElementById("employeeDepartment").value = employee.department;
  document.getElementById("employeeLevel").value = employee.level;

  editingEmployeeId = id;

  document.getElementById("formTitle").textContent = "Edit Employee";
  document.getElementById("saveButton").textContent = "Update Employee";

  window.scrollTo({
    top: 0,
    behavior: "smooth"
  });
}

function deleteEmployee(id) {
  const confirmDelete = confirm("Are you sure you want to remove this employee record?");

  if (confirmDelete) {
    employees = employees.filter(employee => employee.id !== id);
    alert("Employee removed successfully.");
    searchEmployees();
  }
}

function resetForm() {
  document.getElementById("employeeName").value = "";
  document.getElementById("employeeRole").value = "";
  document.getElementById("employeeDepartment").value = "";
  document.getElementById("employeeLevel").value = "";

  editingEmployeeId = null;

  document.getElementById("formTitle").textContent = "Add New Employee";
  document.getElementById("saveButton").textContent = "Add Employee";
}

function clearSearch() {
  document.getElementById("searchInput").value = "";
  document.getElementById("departmentFilter").value = "";
  document.getElementById("roleFilter").value = "";
  document.getElementById("levelFilter").value = "";

  document.getElementById("resultsSection").classList.add("hidden");
  document.getElementById("employeeList").innerHTML = "";
}
