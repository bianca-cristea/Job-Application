import React, { useEffect, useState } from 'react';
import { getAllInterviewsGroupedByCompany } from '../services/api';

export default function AdminInterviewsPage() {
  const [grouped, setGrouped] = useState({});

  useEffect(() => {
    getAllInterviewsGroupedByCompany().then(setGrouped);
  }, []);

  return (
    <div style={{ padding: '1rem' }}>
      <h1>All Interviews by Company</h1>
      {Object.entries(grouped).map(([company, list]) => (
        <div key={company} style={{ marginBottom: '2rem' }}>
          <h2>{company}</h2>
          {list.map(i => (
            <div key={i.id} style={{ border: '1px solid #ccc', padding: '1rem', marginBottom: '1rem' }}>
              <p><b>Date:</b> {new Date(i.scheduledAt).toLocaleString()}</p>
              <p><b>Location:</b> {i.location}</p>
              <p><b>Job:</b> {i.application.job.title}</p>
              <p><b>Candidate:</b> {i.application.user.email}</p>
            </div>
          ))}
        </div>
      ))}
    </div>
  );
}
