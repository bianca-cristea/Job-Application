import React, { useEffect, useState } from 'react';
import {
  getAllReviews,
  getAllReviewsAdmin,
  addReview,
  updateReview,
  deleteReview,
} from '../services/api';
import ReviewForm from '../components/ReviewForm';
import { useAuth } from '../hooks/useAuth';

export default function ReviewsPage({ companyId = 1 }) {
  const [reviews, setReviews] = useState([]);
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState(null);

  const { user } = useAuth();
  const isAdmin = user?.roles.includes('ROLE_ADMIN');

  useEffect(() => {
    if (isAdmin) {
      fetchAllReviewsAdmin();
    } else {
      fetchCompanyReviews();
    }
  }, [companyId, isAdmin]);
function handleAdd(review) {
  fetch('/reviews', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(review),
  }).then(res => {
    if (res.ok) {
      alert('Review added successfully');
    } else {
      alert('Failed to add review');
    }
  });
}

  async function fetchCompanyReviews() {
    try {
      const data = await getAllReviews(companyId);
      setReviews(data);
    } catch (err) {
      setError(err.message);
    }
  }

  async function fetchAllReviewsAdmin() {
    try {
      const data = await getAllReviewsAdmin();
      setReviews(data);
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleAdd(review) {
    try {
      await addReview(review, review.companyId);

      if (isAdmin) fetchAllReviewsAdmin();
      else fetchCompanyReviews();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleUpdate(reviewId, updated) {
    try {
      await updateReview(companyId, reviewId, updated);
      setEditing(null);
      if (isAdmin) fetchAllReviewsAdmin();
      else fetchCompanyReviews();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete(reviewId) {
    try {
      await deleteReview(companyId, reviewId);
      if (isAdmin) fetchAllReviewsAdmin();
      else fetchCompanyReviews();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ padding: '1rem' }}>
      <h1>Reviews for Companies</h1>

      {!isAdmin && (
        <>
          <h2>Add Review</h2>
          <ReviewForm onSubmit={handleAdd} />
        </>
      )}

      {error && <p style={{ color: 'red' }}>{error}</p>}

      <ul style={{ listStyle: 'none', padding: 0 }}>
        {reviews.map((r) => (
          <li key={r.id} style={{ border: '1px solid #ccc', padding: '1rem', margin: '1rem 0' }}>
            {editing === r.id && !isAdmin ? (
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
              <p><b>Company:</b> {r.company?.name}</p>



                {!isAdmin && r.user?.username === user.username && (
                  <>
                    <button onClick={() => setEditing(r.id)} style={{ marginRight: '0.5rem' }}>Edit</button>
                    <button onClick={() => handleDelete(r.id)}>Delete</button>
                  </>
                )}
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}
