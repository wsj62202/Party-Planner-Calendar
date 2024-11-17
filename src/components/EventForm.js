import React, { useState } from 'react';
import { createEvent } from '../services/eventService';

const EventForm = () => {
  const [event, setEvent] = useState({
    title: '',
    date: '',
    time: '',
    location: '',
    type: '',
  });

  const handleChange = (e) => {
    setEvent({ ...event, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await createEvent(event);
    alert("Event created successfully!");
  };

  return (
    <form onSubmit={handleSubmit}>
      <input name="title" value={event.title} onChange={handleChange} placeholder="Title" />
      <input name="date" type="date" value={event.date} onChange={handleChange} />
      <input name="time" type="time" value={event.time} onChange={handleChange} />
      <input name="location" value={event.location} onChange={handleChange} placeholder="Location" />
      <input name="type" value={event.type} onChange={handleChange} placeholder="Type" />
      <button type="submit">Create Event</button>
    </form>
  );
};

export default EventForm;
