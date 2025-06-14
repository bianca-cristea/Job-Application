import React, { useEffect, useState } from "react";
import { fetchJobs, createJob, updateJob, deleteJob, applyToJob } from "../services/api";
import JobForm from "../components/JobForm";
import { useAuth } from "../hooks/useAuth";

export default function Jobs() {
  const { user } = useAuth();
  const [jobs, setJobs] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadJobs();
  }, []);


  async function loadJobs() {
    try {
      const data = await fetchJobs();
      setJobs(data);
      setError(null);
    } catch (err) {
      setError("Failed to load jobs");
    }
  }

  async function handleCreate(job) {
    try {
      await createJob(job);
      await loadJobs();
    } catch (err) {
      setError("Failed to create job");
    }
  }

  async function handleUpdate(id, job) {
    try {
      await updateJob(id, job);
      setEditingId(null);
      await loadJobs();
    } catch (err) {
      setError("Failed to update job");
    }
  }

  async function handleDelete(id) {
    try {
      await deleteJob(id);
      await loadJobs();
    } catch (err) {
      setError("Failed to delete job");
    }
  }

  async function handleApply(id) {
    try {
      await applyToJob(id);
      alert("Applied successfully");
    } catch {
      alert("Failed to apply");
    }
  }




  const isAdmin = user?.roles.includes("ROLE_ADMIN");
  const isUser = user?.roles.includes("ROLE_USER");

  return (
    <div style={{ padding: "1rem" }}>
      <h1>Jobs</h1>

      {isAdmin && (
        <>
          <h2>Create Job</h2>
          <JobForm onSubmit={handleCreate} />

        </>
      )}

      {error && <p style={{ color: "red" }}>{error}</p>}

      <h2>Available Jobs</h2>
      <ul style={{ listStyle: "none", padding: 0 }}>
        {jobs.map((job) => (
          <li
            key={job.id}
            style={{
              marginBottom: "1rem",
              border: "1px solid #ccc",
              padding: "1rem",
            }}
          >
            {editingId === job.id ? (
              <>
                <JobForm
                  initialData={job}
                  onSubmit={(updated) => handleUpdate(job.id, updated)}
                />
                <button onClick={() => setEditingId(null)}>Cancel</button>
              </>
            ) : (
              <>
                <h3>{job.title}</h3>
                <p>{job.description}</p>
                <p>
                  <b>Salary:</b> {job.minSalary} - {job.maxSalary}
                </p>
                <p>
                  <b>Location:</b> {job.location}
                </p>
                <p>
                  <b>Company:</b> {job.company?.name || "N/A"}
                </p>

                {isAdmin && (
                  <>
                    <button onClick={() => setEditingId(job.id)}>Edit</button>
                    <button onClick={() => handleDelete(job.id)}>Delete</button>
                  </>
                )}

                {isUser && (
                  <button onClick={() => handleApply(job.id)}>Apply</button>
                )}
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}
