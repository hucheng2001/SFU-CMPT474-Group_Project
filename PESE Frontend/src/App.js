import React, { useState } from 'react';
import './App.css';

// Replace with actual content and image URLs.
const contentData = {
  interest: [
    { title: 'Linear Algebra: Eigenvalue & Eigenvector', type: 'Video', imageUrl: '/pic_1.jpg' },
    { title: 'C++: Pointers', type: 'Text', imageUrl: '/pic_1.jpg' },
    { title: 'Computer Parts: GPU introduction', type: 'Video', imageUrl: '/pic_1.jpg' },
    { title: 'Learn java', type: 'Video', imageUrl: '/pic_1.jpg' },
    { title: 'Learn C++', type: 'Text', imageUrl: '/pic_1.jpg' },
    { title: 'Learn HTML5', type: 'Video', imageUrl: '/pic_1.jpg' },
    { title: 'Learn JS', type: 'Text', imageUrl: '/pic_1.jpg' },
    // ... other content items
  ],
  improvement: [
    { title: 'Linear Algebra: Vectors & Matrix', type: 'Video', imageUrl: '/pic_1.jpg' },
    { title: 'Machine learning: CNN', type: 'Text', imageUrl: '/pic_1.jpg' },
    { title: 'Machine learning: A* algorithm', type: 'Video', imageUrl: '/pic_1.jpg' },
    // ... other content items
  ],
};

const ContentRow = ({ items }) => {
  const [index, setIndex] = useState(0);

  const hasNext = index < items.length - 3;
  const hasPrev = index > 0;

  const slideNext = () => {
    if (hasNext) setIndex(index + 3);
  };

  const slidePrev = () => {
    if (hasPrev) setIndex(index - 3);
  };

  return (
    <div className="content-row">
      <button onClick={() => slidePrev()} className="slide-arrow left-arrow" disabled={!hasPrev}>&lt;</button>
      <div className="content-row-window">
        <div className="content-row-inner" style={{ transform: `translateX(-${index * 100 / items.length}%)` }}>
          {items.map((item, i) => (
            <div key={i} className="content-item">
              <img src={item.imageUrl} alt={item.title} />
              <h4>{item.title}</h4>
              <p>{item.type}</p>
            </div>
          ))}
        </div>
      </div>
      <button onClick={() => slideNext()} className="slide-arrow right-arrow" disabled={!hasNext}>&gt;</button>
    </div>
  );
};

const App = () => {
  return (
    <div className="App">
      <header>
        <h1>MyEducation</h1>
        <input type="text" placeholder="Search" />
      </header>
      <main>
        <section>
          <h2>You might be interested:</h2>
          <ContentRow items={contentData.interest} />
        </section>
        <section>
          <h2>You might want to improve on:</h2>
          <ContentRow items={contentData.improvement} />
        </section>
      </main>
    </div>
  );
};

export default App;
