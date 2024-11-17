import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [userRole, setUserRole] = useState('');
  const [hosts, setHosts] = useState([]);
  const [currentHost, setCurrentHost] = useState(null);
  const [newEvent, setNewEvent] = useState({
    title: '',
    date: '',
    time: '',
    location: '',
    type: '',
    guests: [],
  });
  const [newGuest, setNewGuest] = useState({ name: '', email: '', role: '', rsvp: false });

  // Fetch hosts data from the backend
  useEffect(() => {
    if (userRole === 'admin') {
      axios.get('/api/hosts')
        .then(response => setHosts(response.data))
        .catch(error => console.error('Error fetching hosts:', error));
    }
  }, [userRole]);

  // Handle role selection
  const handleRoleSelection = (role) => {
    setUserRole(role);
    if (role === 'host') {
      setCurrentHost(prompt('Enter your name:'));
    }
  };

  // Create a new event
  const handleCreateEvent = () => {
    axios.post(`/api/hosts/${currentHost}/events`, newEvent)
      .then(response => {
        alert('Event created successfully!');
        setNewEvent({ title: '', date: '', time: '', location: '', type: '', guests: [] });
      })
      .catch(error => console.error('Error creating event:', error));
  };

  // Add a guest to the new event
  const handleAddGuest = () => {
    setNewEvent({ ...newEvent, guests: [...newEvent.guests, newGuest] });
    setNewGuest({ name: '', email: '', role: '', rsvp: false });
  };

  return (
    <div className="App">
      <h1>Welcome to the Calendar App</h1>
      
      {/* User Role Selection */}
      {!userRole && (
        <div>
          <button onClick={() => handleRoleSelection('host')}>Host</button>
          <button onClick={() => handleRoleSelection('admin')}>Admin</button>
          <button onClick={() => alert('Goodbye!')}>Exit</button>
        </div>
      )}

      {/* Admin View */}
      {userRole === 'admin' && (
        <div>
          <h2>All Hosts and Events</h2>
          {hosts.length === 0 ? (
            <p>No hosts available.</p>
          ) : (
            hosts.map(host => (
              <div key={host.name}>
                <h3>Host: {host.name}</h3>
                {host.events.map(event => (
                  <div key={event.title}>
                    <p>{event.title} - {event.date} at {event.time}</p>
                    <p>Location: {event.location} | Type: {event.type}</p>
                    <p>Guests:</p>
                    <ul>
                      {event.guests.map(guest => (
                        <li key={guest.email}>
                          {guest.name} ({guest.email}) - RSVP: {guest.rsvp ? 'Yes' : 'No'}
                        </li>
                      ))}
                    </ul>
                  </div>
                ))}
              </div>
            ))
          )}
        </div>
      )}

      {/* Host View */}
      {userRole === 'host' && currentHost && (
        <div>
          <h2>Welcome, {currentHost}!</h2>
          <h3>Create a New Event</h3>
          <input
            type="text"
            placeholder="Title"
            value={newEvent.title}
            onChange={(e) => setNewEvent({ ...newEvent, title: e.target.value })}
          />
          <input
            type="date"
            value={newEvent.date}
            onChange={(e) => setNewEvent({ ...newEvent, date: e.target.value })}
          />
          <input
            type="time"
            value={newEvent.time}
            onChange={(e) => setNewEvent({ ...newEvent, time: e.target.value })}
          />
          <input
            type="text"
            placeholder="Location"
            value={newEvent.location}
            onChange={(e) => setNewEvent({ ...newEvent, location: e.target.value })}
          />
          <input
            type="text"
            placeholder="Type"
            value={newEvent.type}
            onChange={(e) => setNewEvent({ ...newEvent, type: e.target.value })}
          />
          
          <h3>Add Guests</h3>
          <input
            type="text"
            placeholder="Guest Name"
            value={newGuest.name}
            onChange={(e) => setNewGuest({ ...newGuest, name: e.target.value })}
          />
          <input
            type="email"
            placeholder="Guest Email"
            value={newGuest.email}
            onChange={(e) => setNewGuest({ ...newGuest, email: e.target.value })}
          />
          <input
            type="text"
            placeholder="Role"
            value={newGuest.role}
            onChange={(e) => setNewGuest({ ...newGuest, role: e.target.value })}
          />
          <label>
            RSVP:
            <input
              type="checkbox"
              checked={newGuest.rsvp}
              onChange={(e) => setNewGuest({ ...newGuest, rsvp: e.target.checked })}
            />
          </label>
          <button onClick={handleAddGuest}>Add Guest</button>

          <button onClick={handleCreateEvent}>Create Event</button>
        </div>
      )}
    </div>
  );
}

export default App;
