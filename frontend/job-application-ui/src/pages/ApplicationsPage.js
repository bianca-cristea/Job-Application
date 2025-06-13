import React, { useEffect, useState } from 'react';
import { getAllApplications, createApplication, updateApplication, deleteApplication } from '../services/api';
import ApplicationForm from '../components/ApplicationForm';

export default function ApplicationsPage() {
  const [applications, setApplications] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadApplications();
  }, []);

  async function loadApplications() {
    try {
      const data = await getAllApplications();
      setApplications(data);
      setError(null);
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleCreate(application) {
    try {
      await createApplication(application);
      await loadApplications();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleUpdate(id, application) {
    try {
      await updateApplication(id, application);
      setEditingId(null);
      await loadApplications();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete(id) {
    try {
      await deleteApplication(id);
      await loadApplications();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ padding: '1rem' }}>
      <h1>Applications</h1>

      <h2>Create New Application</h2>
      <ApplicationForm onSubmit={handleCreate} />

      {error && <p style={{ color: 'red' }}>{error}</p>}

      <h2>Existing Applications</h2>
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {applications.map((app) => (
          <li key={app.id} style={{ marginBottom: '1rem', border: '1px solid #ccc', padding: '1rem' }}>
            {editingId === app.id ? (
              <>
                <ApplicationForm
                  initialData={app}

                  onSubmit={(updated) => handleUpdate(app.id, updated)}
                />
                <button onClick={() => setEditingId(null)}>Cancel</button>
              </>
            ) : (
              <>
                <p><b>ID:</b> {app.id}</p>
                <p><b>Status:</b> {app.status}</p>
                <p><b>User ID:</b> {app.user?.id}</p>
                <p><b>Job ID:</b> {app.job?.id}</p>
                <button onClick={() => setEditingId(app.id)} style={{ marginRight: '0.5rem' }}>Edit</button>
                <button onClick={() => handleDelete(app.id)}>Delete</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}