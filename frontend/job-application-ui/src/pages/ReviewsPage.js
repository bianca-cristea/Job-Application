import React, { useEffect, useState } from 'react';
import {
  getAllReviews,
  addReview,
  updateReview,
  deleteReview,
} from '../services/api';
import ReviewForm from '../components/ReviewForm';

export default function ReviewsPage({ companyId = 1 }) {
  const [reviews, setReviews] = useState([]);
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchReviews();
  }, [companyId]);

  async function fetchReviews() {
    try {
      const data = await getAllReviews(companyId);
      setReviews(data);
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleAdd(review) {
    try {
      await addReview(review, companyId);
      await fetchReviews();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleUpdate(reviewId, updated) {
    try {
      await updateReview(companyId, reviewId, updated);
      setEditing(null);
      await fetchReviews();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete(reviewId) {
    try {
      await deleteReview(companyId, reviewId);
      await fetchReviews();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ padding: '1rem' }}>
      <h1>Reviews for Company {companyId}</h1>

      <ReviewForm onSubmit={handleAdd} />

      {error && <p style={{ color: 'red' }}>{error}</p>}

      <ul style={{ listStyle: 'none', padding: 0 }}>
        {reviews.map((r) => (
          <li key={r.id} style={{ border: '1px solid #ccc', padding: '1rem', margin: '1rem 0' }}>
            {editing === r.id ? (
              <>
                <ReviewForm
                  initialData={r}
                  onSubmit={(updated) => handleUpdate(r.id, updated)}
                />
                <button onClick={() => setEditing(null)}>Cancel</button>
              </>
            ) : (
              <>
                <p><b>Title:</b> {r.title}</p>
                <p><b>Description:</b> {r.description}</p>
                <p><b>Rating:</b> {r.rating}</p>
                <button onClick={() => setEditing(r.id)} style={{ marginRight: '0.5rem' }}>Edit</button>
                <button onClick={() => handleDelete(r.id)}>Delete</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}
