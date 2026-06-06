const employees = [
  {
    id: 1,
    name: "Rafael Sales",
    role: "Project Manager",
    department: "Projects",
    level: "Senior"
  },
  {
    id: 2,
    name: "Ana Martins",
    role: "HR Analyst",
    department: "HR",
    level: "Junior"
  },
  {
    id: 3,
    name: "Lucas Pereira",
    role: "Software Developer",
    department: "Development",
    level: "Mid-Level"
  },
  {
    id: 4,
    name: "Sofia Almeida",
    role: "Sales Supervisor",
    department: "Sales",
    level: "Senior"
  },
  {
    id: 5,
    name: "Daniel Costa",
    role: "IT Technician",
    department: "IT Support",
    level: "Junior"
  }
];

function searchEmployees() {
  const department = document.getElementById("departmentFilter").value;
  const role = document.getElementById("roleFilter").value;
  const level = document.getElementById("levelFilter").value;

  const results = employees.filter(employee => {
    const matchesDepartment = department === "" || employee.department === department;
    const matchesRole = role === "" || employee.role === role;
    const matchesLevel = level === "" || employee.level === level;

    return matchesDepartment && matchesRole && matchesLevel;
  });

  if (results.length === 0) {
    alert("No employees found with the selected filters.");
    return;
  }

  let message = "Employees found:\n\n";

  results.forEach(employee => {
    message +=
      `ID: ${employee.id}\n` +
      `Name: ${employee.name}\n` +
      `Role: ${employee.role}\n` +
      `Department: ${employee.department}\n` +
      `Level: ${employee.level}\n\n`;
  });

  alert(message);
}

function clearFilters() {
  document.getElementById("departmentFilter").value = "";
  document.getElementById("roleFilter").value = "";
  document.getElementById("levelFilter").value = "";
}
