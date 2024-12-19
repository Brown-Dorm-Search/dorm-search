import React from 'react';
import './styles/About.css'; // Create and import the corresponding CSS for styling

/*
Information about the people who made this project possible
*/
const teamMembers = [
  {
    name: 'David Chanin',
    role: 'Backend Programmer and Product Design',
    image: 'https://media.licdn.com/dms/image/v2/D4E03AQGcmzaRMPKvHw/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1692817995449?e=1739404800&v=beta&t=6mjybPmMf0PNlJJLfbZ8bXVpZ101j34K4uf_aATpuuY',
    linkedin: 'https://www.linkedin.com/in/david-chanin/',
  },
  {
    name: 'Kaley Newlin',
    role: 'Frontend Programmer and Outreach',
    image: 'https://media.licdn.com/dms/image/v2/D4E03AQEWbIQOT6wq2Q/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1697987028624?e=1739404800&v=beta&t=-5TUDJL75BdIfFl0ygS4KNNvjTQ1APW-sd6FsuJ-9aM',
    linkedin: 'https://www.linkedin.com/in/kaley-newlin/',
  },
  {
    name: 'Jackson Osterhus',
    role: 'Backend Programmer and Data Engineer',
    image: 'https://media.licdn.com/dms/image/v2/D4D03AQHlnj6KFSOrpA/profile-displayphoto-shrink_200_200/B4DZOynZ3rHYAg-/0/1733868494587?e=1739404800&v=beta&t=dSb-Bz7sNjoroqYFMQ_Ar7YlvQ8kVlQPZYIeZzeSGzo',
    linkedin: 'https://www.linkedin.com/in/jackson-osterhus',
  },
  {
    name: 'Keyan Rahimi',
    role: 'UI/UX Designer and Programmer',
    image: 'https://media.licdn.com/dms/image/v2/D4E03AQGcgalh-lXwRQ/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1716389609064?e=1739404800&v=beta&t=4KVR3J27Q-G8Nq25YRCy9-Luq4NuEs51ejND7O2-p60',
    linkedin: 'https://www.linkedin.com/in/keyanrahimi/',
  },
];

const About = () => {
  return (
    <div className="about-page">
      {/* Description Section */}
      <section className="description">
        <h1>About Brown Dorm Search</h1>
        <p>
          Welcome! Brown Dorm Search aims to streamlize the housing lottery process at Brown University.
        </p>
      </section>

      {/* Team Section */}
      <section className="team">
        <h2>Meet Our Team</h2>
        <div className="team-members">
          {teamMembers.map((member, index) => (
            <div key={index} className="team-member">
              <img src={member.image} alt={member.name} className="team-member-image" />
              <h3>{member.name}</h3>
              <p>{member.role}</p>
              <a href={member.linkedin} target="_blank" rel="noopener noreferrer" className="linkedin-link">
                LinkedIn
              </a>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

export default About;