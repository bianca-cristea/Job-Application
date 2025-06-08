import React, { useEffect, useState } from 'react';
import {
  getInterviewByApplication,
  createInterview,
  updateInterview,
  deleteInterview,
} from '../services/api';
import InterviewForm from '../components/InterviewForm';

export default function InterviewPage({ applicationId = 1 }) {
  const [interview, setInterview] = useState(null);
  const [editing, setEditing] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchInterview();
  }, [applicationId]);

  async function fetchInterview() {
    try {
      const data = await getInterviewByApplication(applicationId);
      setInterview(data);
    } catch (err) {
      setInterview(null);
    }
  }

  async function handleCreate(interviewData) {
    try {
      await createInterview(applicationId, interviewData);
      await fetchInterview();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleUpdate(interviewData) {
    try {
      await updateInterview(interview.id, interviewData);
      setEditing(false);
      await fetchInterview();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete() {
    try {
      await deleteInterview(interview.id);
      setInterview(null);
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ padding: '1rem' }}>
      <h1>Interview for Application {applicationId}</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {!interview ? (
        <>
          <p>No interview scheduled.</p>
          <InterviewForm onSubmit={handleCreate} />
        </>
      ) : editing ? (
        <>
          <InterviewForm
            initialData={interview}
            onSubmit={handleUpdate}
          />
          <button onClick={() => setEditing(false)}>Cancel</button>
        </>
      ) : (
        <div style={{ border: '1px solid #ccc', padding: '1rem' }}>
          <p><b>Scheduled At:</b> {new Date(interview.scheduledAt).toLocaleString()}</p>
          <p><b>Location:</b> {interview.location}</p>
          <button onClick={() => setEditing(true)} style={{ marginRight: '0.5rem' }}>Edit</button>
          <button onClick={handleDelete}>Delete</button>
        </div>
      )}
    </div>
  );
}
