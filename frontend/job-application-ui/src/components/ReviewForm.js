import React, { useState, useEffect } from 'react';

export default function ReviewForm({ initialData = {}, onSubmit }) {
  const [companies, setCompanies] = useState([]);
  const [companyId, setCompanyId] = useState(initialData.company?.id || '');
  const [title, setTitle] = useState(initialData.title || '');
  const [description, setDescription] = useState(initialData.description || '');
  const [rating, setRating] = useState(initialData.rating || 0);
  const API_BASE = "http://localhost:8080";

  function getAuthHeaders() {
    const token = localStorage.getItem('token');
    return {
      'Content-Type': 'application/json',
      ...(token && { Authorization: `Bearer ${token}` }),
    };
  }

  useEffect(() => {
    fetch(`${API_BASE}/companies`, {
      headers: getAuthHeaders(),
    })
      .then(res => {
        if (!res.ok) throw new Error('Unauthorized');
        return res.json();
      })
      .then(data => setCompanies(data))
      .catch(err => {
        console.error('Eroare la fetch:', err);
      });
  }, []);

  function handleSubmit(e) {
    e.preventDefault();

    if (!companyId) {
      alert('Please select a company');
      return;
    }

    onSubmit({
      title,
      description,
      rating,
      companyId: parseInt(companyId)
    });

    setTitle('');
    setDescription('');
    setRating(0);
    setCompanyId('');
  }

  return (
    <div style={{
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      height: '80vh',
      padding: '20px',
      boxSizing: 'border-box',
      backgroundColor: '#f0f2f5',
    }}>
      <form onSubmit={handleSubmit} style={{
        padding: '2rem',
        border: '1px solid #ddd',
        borderRadius: '8px',
        maxWidth: '400px',
        width: '100%',
        backgroundColor: '#fff',
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        boxSizing: 'border-box',
      }}>
        <select
          value={companyId}
          onChange={(e) => setCompanyId(e.target.value)}
          required
          style={{
            width: '100%',
            padding: '10px',
            marginBottom: '15px',
            borderRadius: '4px',
            border: '1px solid #ccc',
            fontSize: '1rem',
            boxSizing: 'border-box',
          }}
        >
          <option value="">Select company</option>
          {companies.map(c => (
            <option key={c.id} value={c.id}>{c.name}</option>
          ))}
        </select>

        <input
          type="text"
          placeholder="Title"
          value={title}
          required
          onChange={(e) => setTitle(e.target.value)}
          style={{
            width: '100%',
            padding: '10px',
            marginBottom: '15px',
            borderRadius: '4px',
            border: '1px solid #ccc',
            fontSize: '1rem',
            boxSizing: 'border-box',
          }}
        />

        <textarea
          placeholder="Description"
          value={description}
          required
          onChange={(e) => setDescription(e.target.value)}
          style={{
            width: '100%',
            padding: '10px',
            marginBottom: '15px',
            borderRadius: '4px',
            border: '1px solid #ccc',
            fontSize: '1rem',
            minHeight: '80px',
            resize: 'vertical',
            boxSizing: 'border-box',
          }}
        />

        <input
          type="number"
          placeholder="Rating (0-5)"
          min="0"
          max="5"
          value={rating}
          required
          onChange={(e) => setRating(Number(e.target.value))}
          style={{
            width: '100%',
            padding: '10px',
            marginBottom: '20px',
            borderRadius: '4px',
            border: '1px solid #ccc',
            fontSize: '1rem',
            boxSizing: 'border-box',
          }}
        />

        <button
          type="submit"
          style={{
            width: '100%',
            padding: '12px',
            backgroundColor: '#007bff',
            color: 'white',
            fontSize: '1rem',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            transition: 'background-color 0.3s',
          }}
          onMouseEnter={e => e.currentTarget.style.backgroundColor = '#0056b3'}
          onMouseLeave={e => e.currentTarget.style.backgroundColor = '#007bff'}
        >
          Submit Review
        </button>
      </form>
    </div>
  );
}
