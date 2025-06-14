const API_BASE = "http://localhost:8080";

function getAuthHeaders() {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    ...(token && { Authorization: `Bearer ${token}` }),
  };
}

export async function fetchJobs() {
  const res = await fetch(`${API_BASE}/jobs`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Failed to fetch jobs');
  return res.json();
}

export async function createJob(job) {
  const res = await fetch(`${API_BASE}/jobs`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(job),
  });
  if (!res.ok) throw new Error('Failed to create job');
}

export async function updateJob(id, updatedJob) {
  const res = await fetch(`${API_BASE}/jobs/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(updatedJob),
  });
  if (!res.ok) throw new Error('Failed to update job');
}

export async function deleteJob(id) {
  const res = await fetch(`${API_BASE}/jobs/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders(),
  });
  if (!res.ok) throw new Error('Failed to delete job');
}
export const applyToJob = async (jobId) => {
  try {
    const res = await fetch(`/jobs/${jobId}/apply`, {
      method: "POST",
    });
    if (res.ok) {
      alert("Applied successfully");
    } else {
      alert("Failed to apply");
    }
  } catch {
    alert("Failed to apply");
  }
};

export async function getAllApplications() {
  const res = await fetch(`${API_BASE}/applications`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error fetching applications');
  return res.json();
}

export const createApplication = async (userId, jobId, applicationData) => {
  const response = await fetch(`http://localhost:8080/applications/user/{userId}/job/{jobId}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(applicationData),
  });

  if (!response.ok) {
    throw new Error("Failed to create application");
  }

  return await response.json();
};


export async function updateApplication(id, updated) {
  const res = await fetch(`${API_BASE}/applications/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(updated),
  });
  if (!res.ok) throw new Error('Error updating application');
  return res.json();
}

export async function deleteApplication(id) {
  const res = await fetch(`${API_BASE}/applications/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders(),
  });
  if (!res.ok) throw new Error('Error deleting application');
}

export async function getAllCompanies() {
  const res = await fetch(`${API_BASE}/companies`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error fetching companies');
  return res.json();
}

export async function createCompany(company) {
  const res = await fetch(`${API_BASE}/companies`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(company),
  });
  if (!res.ok) throw new Error('Error creating company');
}

export async function updateCompany(id, updated) {
  const res = await fetch(`${API_BASE}/companies/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(updated),
  });
  if (!res.ok) throw new Error('Error updating company');
}

export async function deleteCompanyById(id) {
  const res = await fetch(`${API_BASE}/companies/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders(),
  });
  if (!res.ok) throw new Error('Error deleting company');
}

export async function getAllReviews(companyId) {
  const res = await fetch(`${API_BASE}/companies/${companyId}/reviews`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error fetching reviews');
  return res.json();
}

export async function addReview(review, companyId) {
  const res = await fetch(`${API_BASE}/companies/${companyId}/reviews`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(review),
  });
  if (!res.ok) throw new Error('Error creating review');
}

export async function updateReview(companyId, reviewId, updated) {
  const res = await fetch(`${API_BASE}/companies/${companyId}/reviews/${reviewId}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(updated),
  });
  if (!res.ok) throw new Error('Error updating review');
}

export async function deleteReview(companyId, reviewId) {
  const res = await fetch(`${API_BASE}/companies/${companyId}/reviews/${reviewId}`, {
    method: 'DELETE',
    headers: getAuthHeaders(),
  });
  if (!res.ok) throw new Error('Error deleting review');
}

export async function getInterviewByApplication(applicationId) {
  const res = await fetch(`${API_BASE}/interviews/application/${applicationId}`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error fetching interview');
  return res.json();
}

export async function createInterview(applicationId, interview) {
  const res = await fetch(`${API_BASE}/interviews/application/${applicationId}`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(interview),
  });
  if (!res.ok) throw new Error('Error creating interview');
}

export async function updateInterview(id, interview) {
  const res = await fetch(`${API_BASE}/interviews/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(interview),
  });
  if (!res.ok) throw new Error('Error updating interview');
}

export async function deleteInterview(id) {
  const res = await fetch(`${API_BASE}/interviews/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders(),
  });
  if (!res.ok) throw new Error('Error deleting interview');
}

export async function getAllRoles() {
  const res = await fetch(`${API_BASE}/roles`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Failed to fetch roles');
  return res.json();
}

export async function createRole(role) {
  const res = await fetch(`${API_BASE}/roles`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(role),
  });
  if (!res.ok) throw new Error('Failed to create role');
}

export async function updateRole(id, role) {
  const res = await fetch(`${API_BASE}/roles/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(role),
  });
  if (!res.ok) throw new Error('Failed to update role');
}

export async function deleteRole(id) {
  const res = await fetch(`${API_BASE}/roles/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders(),
  });
  if (!res.ok) throw new Error('Failed to delete role');
}

export async function getAllUsers(page = 0, size = 10, sortBy = 'username') {
  const response = await fetch(`${API_BASE}/users?page=${page}&size=${size}&sortBy=${sortBy}`, {
    headers: getAuthHeaders(),
  });
  if (!response.ok) throw new Error('Failed to fetch users');
  return await response.json();
}





export async function createUser(user) {
  const res = await fetch(`${API_BASE}/users`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(user),
  });
  if (!res.ok) throw new Error('Failed to create user');
}

export async function updateUser(id, user) {
  const res = await fetch(`${API_BASE}/users/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(user),
  });
  if (!res.ok) throw new Error('Failed to update user');
}
export async function getMyInterviews() {
  const res = await fetch(`${API_BASE}/interviews/user`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Failed to fetch user interviews');
  return res.json();
}

export async function getAllInterviewsGroupedByCompany() {
  const res = await fetch(`${API_BASE}/interviews/admin`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Failed to fetch admin interviews');
  return res.json();
}


export async function deleteUser(id) {
  const res = await fetch(`${API_BASE}/users/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders(),
  });
  if (!res.ok) throw new Error('Failed to delete user');
}
