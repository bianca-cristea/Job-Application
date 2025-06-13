import React, { useEffect, useState } from 'react';
import { getMyInterviews } from '../services/api';

export default function UserInterviewsPage() {
  const [interviews, setInterviews] = useState([]);

  useEffect(() => {
    getMyInterviews().then(setInterviews);
  }, []);

  return (
    <div style={{ padding: '1rem' }}>
      <h1>My Interviews</h1>
      {interviews.map(i => (
        <div key={i.id} style={{ border: '1px solid #ccc', marginBottom: '1rem', padding: '1rem' }}>
          <p><b>Date:</b> {new Date(i.scheduledAt).toLocaleString()}</p>
          <p><b>Location:</b> {i.location}</p>
          <p><b>Job:</b> {i.application.job.title}</p>
          <p><b>Company:</b> {i.application.job.company.name}</p>
        </div>
      ))}
    </div>
  );
}
