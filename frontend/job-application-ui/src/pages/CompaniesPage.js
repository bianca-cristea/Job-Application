import React, { useEffect, useState } from 'react';
import {
  getAllCompanies,
  createCompany,
  updateCompany,
  deleteCompanyById,
} from '../services/api';
import CompanyForm from '../components/CompanyForm';
import { useAuth } from '../hooks/useAuth';

export default function CompaniesPage() {
  const [companies, setCompanies] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState(null);

  const { user } = useAuth();
  const isAdmin = user?.roles.includes('ROLE_ADMIN');

  useEffect(() => {
    loadCompanies();
  }, []);

  async function loadCompanies() {
    try {
      const data = await getAllCompanies();
      setCompanies(data);
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleCreate(company) {
    try {
      await createCompany(company);
      await loadCompanies();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleUpdate(id, updatedCompany) {
    try {
      await updateCompany(id, updatedCompany);
      setEditingId(null);
      await loadCompanies();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete(id) {
    try {
      await deleteCompanyById(id);
      await loadCompanies();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ padding: '1rem' }}>
      <h1>Companies</h1>

      {isAdmin && (
        <>
          <h2>Create Company</h2>
          <CompanyForm onSubmit={handleCreate} />
        </>
      )}

      {error && <p style={{ color: 'red' }}>{error}</p>}


      <ul style={{ listStyle: 'none', padding: 0 }}>
        {companies.map((company) => (
          <li key={company.id} style={{ border: '1px solid #ccc', padding: '1rem', marginBottom: '1rem' }}>
            {editingId === company.id ? (
              isAdmin ? (
                <>
                  <CompanyForm
                    initialData={company}
                    onSubmit={(updated) => handleUpdate(company.id, updated)}
                  />
                  <button onClick={() => setEditingId(null)}>Cancel</button>
                </>
              ) : (
                <>
                  <p><b>Name:</b> {company.name}</p>
                  <p><b>Description:</b> {company.description}</p>
                </>
              )
            ) : (
              <>
                <p><b>Name:</b> {company.name}</p>
                <p><b>Description:</b> {company.description}</p>
                {isAdmin && (
                  <>
                    <button onClick={() => setEditingId(company.id)} style={{ marginRight: '0.5rem' }}>Edit</button>
                    <button onClick={() => handleDelete(company.id)}>Delete</button>
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
