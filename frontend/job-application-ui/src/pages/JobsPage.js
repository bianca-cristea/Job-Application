import React, { useEffect, useState } from 'react';
import { fetchJobs, createJob, updateJob, deleteJob } from '../services/api';
import JobForm from '../components/JobForm';

export default function JobsPage() {
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
      setError(err.message);
    }
  }

  async function handleCreate(job) {
    try {
      await createJob(job);
      await loadJobs();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleUpdate(id, job) {
    try {
      await updateJob(id, job);
      setEditingId(null);
      await loadJobs();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete(id) {
    try {
      await deleteJob(id);
      await loadJobs();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ padding: '1rem' }}>
      <h1>Jobs</h1>

      <h2>Create New Job</h2>
      <JobForm onSubmit={handleCreate} />

      {error && <p style={{ color: 'red' }}>{error}</p>}

      <h2>Existing Jobs</h2>
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {jobs.map((job) => (
          <li
            key={job.id}
            style={{ marginBottom: '1rem', border: '1px solid #ccc', padding: '1rem' }}
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
                <p><b>ID:</b> {job.id}</p>
                <p><b>Title:</b> {job.title}</p>
                <p><b>Description:</b> {job.description}</p>
                <p><b>Salary:</b> {job.minSalary} - {job.maxSalary}</p>
                <p><b>Location:</b> {job.location}</p>
                <p><b>Company ID:</b> {job.company?.id || 'N/A'}</p>
                <button
                  onClick={() => setEditingId(job.id)}
                  style={{ marginRight: '0.5rem' }}
                >
                  Edit
                </button>
                <button onClick={() => handleDelete(job.id)}>Delete</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}