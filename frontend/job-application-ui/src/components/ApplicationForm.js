import React, { useState } from 'react';

export default function ApplicationForm({ onSubmit, initialData, userId, jobId }) {
  const [status, setStatus] = useState(initialData?.status || '');

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({ status, user: { id: userId }, job: { id: jobId } });
  }

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: '1rem' }}>
      <label>
        Status:
        <input
          type="text"
          value={status}
          onChange={(e) => setStatus(e.target.value)}
          required
          style={{ marginLeft: '0.5rem' }}
        />
      </label>
      <button type="submit" style={{ marginLeft: '1rem' }}>
        {initialData ? 'Update' : 'Create'}
      </button>
    </form>
  );
}
