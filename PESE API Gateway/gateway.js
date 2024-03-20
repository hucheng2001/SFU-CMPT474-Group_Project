const fetch = require('node-fetch');
const express = require('express');

const gateway = express();
gateway.use(express.json());

const PORT = process.env.PORT || 8080;

gateway.listen(PORT, () => {
  console.log(`Listening on port ${PORT}`);
});


//API Endpoints
//format: /api/{microservice name}/{resource}

//TEMPLATE FREE TO BASE OFF OF. (for GET)
//Test your API is running on cloud run.
gateway.get("/api/course-resources/test", (req, res) => {
    const COURSE = req.params.course;

    //request to course-resource microservice

    //response (success/fail)
    const dummyData = "RESPONSE!";
    res.send({ data : dummyData});
})

gateway.get("/api/course-resources/:course", async (req, res) => {
  res.set('Access-Control-Allow-Origin', '*');

  const COURSE = req.params.course;
  try {
    switch (COURSE) {
      case 'math': 
        const response = await fetch("https://pese-course-resources-sehicwjdpq-uw.a.run.app/course-resources/links/math");
        const json = await response.json();

        res.send(json);
        break;
      default: 
        res.sendStatus(500);
    }
  } catch(error) {
    console.log(error);
    res.sendStatus(500);
  }
})

gateway.get("/api/user-preference/favourites/:userId", async (req, res) => {
  res.set('Access-Control-Allow-Origin', '*');

  const userId = req.params.userId;
  try {
    const response = await fetch(`https://pese-user-preference-sehicwjdpq-uw.a.run.app/user-preference/favourites/${userId}`); //FIX
    const json = await response.json();

    res.send(json);
  } catch(error) {
    console.log(error);
    res.sendStatus(500);
  }
})

gateway.get("/api/search-engine/request/:input", async (req, res) => {
  res.set('Access-Control-Allow-Origin', '*');

  const userId = req.params.userId;
  try {
    const response = await fetch(`https://pese-search-engine-sehicwjdpq-uw.a.run.app/search-engine/request/${userId}`); //FIX
    const json = await response.json();

    res.send(json);
  } catch(error) {
    console.log(error);
    res.sendStatus(500);
  }
})