import React, { useState } from 'react';

export default function JobForm({ onSubmit, initialData }) {
  const [title, setTitle] = useState(initialData?.title || '');
  const [description, setDescription] = useState(initialData?.description || '');
  const [minSalary, setMinSalary] = useState(initialData?.minSalary || '');
  const [maxSalary, setMaxSalary] = useState(initialData?.maxSalary || '');
  const [location, setLocation] = useState(initialData?.location || '');
  const [companyId, setCompanyId] = useState(initialData?.company?.id || '');

  // Pentru simplificare, compania va fi doar un input text cu ID (poți extinde cu dropdown)

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({
      title,
      description,
      minSalary,
      maxSalary,
      location,
      company: { id: companyId }, // backend-ul trebuie să accepte asta
    });
  }

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: '1rem' }}>
      <div>
        <label>
          Title:
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            style={{ marginLeft: '0.5rem' }}
          />
        </label>
      </div>
      <div style={{ marginTop: '0.5rem' }}>
        <label>
          Description:
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
            style={{ marginLeft: '0.5rem', width: '100%', height: '60px' }}
          />
        </label>
      </div>
      <div style={{ marginTop: '0.5rem' }}>
        <label>
          Min Salary:
          <input
            type="text"
            value={minSalary}
            onChange={(e) => setMinSalary(e.target.value)}
            style={{ marginLeft: '0.5rem' }}
          />
        </label>
      </div>
      <div style={{ marginTop: '0.5rem' }}>
        <label>
          Max Salary:
          <input
            type="text"
            value={maxSalary}
            onChange={(e) => setMaxSalary(e.target.value)}
            style={{ marginLeft: '0.5rem' }}
          />
        </label>
      </div>
      <div style={{ marginTop: '0.5rem' }}>
        <label>
          Location:
          <input
            type="text"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
            style={{ marginLeft: '0.5rem' }}
          />
        </label>
      </div>
      <div style={{ marginTop: '0.5rem' }}>
        <label>
          Company ID:
          <input
            type="number"
            value={companyId}
            onChange={(e) => setCompanyId(e.target.value)}
            required
            style={{ marginLeft: '0.5rem' }}
          />
        </label>
      </div>
      <button type="submit" style={{ marginTop: '0.5rem' }}>
        {initialData ? 'Update' : 'Create'}
      </button>
    </form>
  );
}
