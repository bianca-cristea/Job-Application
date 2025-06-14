import React, { useState, useEffect } from "react";
import { getAllCompanies } from "../services/api";

export default function JobForm({ initialData = {}, onSubmit }) {
  const [formData, setFormData] = useState({
    title: initialData.title || "",
    description: initialData.description || "",
    minSalary: initialData.minSalary || "",
    maxSalary: initialData.maxSalary || "",
    location: initialData.location || "",
    companyId: initialData.company?.id || "",
  });

  const [companies, setCompanies] = useState([]);

  useEffect(() => {
    async function loadCompanies() {
      try {
        const data = await getAllCompanies();
        setCompanies(data);
      } catch (err) {
        console.error("Failed to load companies", err);
      }
    }
    loadCompanies();
  }, []);

  useEffect(() => {
    if (initialData && initialData.id) {
      setFormData({
        title: initialData.title || "",
        description: initialData.description || "",
        minSalary: initialData.minSalary || "",
        maxSalary: initialData.maxSalary || "",
        location: initialData.location || "",
        companyId: initialData.company?.id || "",
      });
    }
  }, [initialData]);

  function handleSubmit(e) {
    e.preventDefault();

    onSubmit({
      ...formData,
      minSalary: Number(formData.minSalary),
      maxSalary: Number(formData.maxSalary),
      company: { id: Number(formData.companyId) },
    });

    if (!initialData.id) {
      setFormData({
        title: "",
        description: "",
        minSalary: "",
        maxSalary: "",
        location: "",
        companyId: "",
      });
    }
  }

  return (
    <form className="form" onSubmit={handleSubmit}>
      <input
        className="input"
        value={formData.title}
        onChange={e => setFormData({ ...formData, title: e.target.value })}
        placeholder="Title"
        required
      />
      <textarea
        className="textarea"
        value={formData.description}
        onChange={e => setFormData({ ...formData, description: e.target.value })}
        placeholder="Description"
        required
      />
      <input
        className="input"
        type="number"
        value={formData.minSalary}
        onChange={e => setFormData({ ...formData, minSalary: e.target.value })}
        placeholder="Min Salary"
      />
      <input
        className="input"
        type="number"
        value={formData.maxSalary}
        onChange={e => setFormData({ ...formData, maxSalary: e.target.value })}
        placeholder="Max Salary"
      />
      <input
        className="input"
        value={formData.location}
        onChange={e => setFormData({ ...formData, location: e.target.value })}
        placeholder="Location"
      />
      <select
        className="select"
        value={formData.companyId}
        onChange={e => setFormData({ ...formData, companyId: e.target.value })}
        required
      >
        <option value="" disabled>
          Select company
        </option>
        {companies.map(c => (
          <option key={c.id} value={c.id}>
            {c.name}
          </option>
        ))}
      </select>
      <button className="button" type="submit">Save</button>
    </form>
  );
}
