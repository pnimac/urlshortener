import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

function App() {
  const [longUrl, setLongUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/shorten', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ longUrl }),
      });

      if (response.ok) {
        const data = await response.json();
        setShortUrl(data.shortUrl);
      } else {
        console.error('Error shortening URL');
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="container form-container p-4">
        <div className="header-bar d-flex align-items-center justify-content-between mb-4">
          <h1 className="mb-0">URL Shortener</h1>
          <i className="bi bi-flower1"></i>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="longUrl" className="form-label">Enter your long URL:</label>
            <input
              type="text"
              className="form-control"
              id="longUrl"
              value={longUrl}
              onChange={(e) => setLongUrl(e.target.value)}
              placeholder="https://www.example.com/long/url/to/be/shortened"
              required
            />
          </div>
          <button type="submit" className="btn btn-primary">Shorten URL</button>
        </form>
        {shortUrl && (
          <div className="mt-4">
            <div className="alert alert-info" role="alert">
              <h4 className="alert-heading">Shortened URL:</h4>
              <input
                type="text"
                className="form-control"
                value={shortUrl}
                readOnly
              />
              <hr />
              <a href={shortUrl} target="_blank" rel="noopener noreferrer" className="btn btn-link">Open Shortened URL</a>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
