<a name="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/ShAd0W20/DAM2_JavaFX">
    <img src="https://avatars.githubusercontent.com/u/43751763?v=4" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">JavaFX football app</h3>

  <p align="center">
    A JavaFX football app
    <br />
    <a href="https://github.com/ShAd0W20/DAM2_JavaFX"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/ShAd0W20/DAM2_JavaFX">View Demo</a>
    ·
    <a href="https://github.com/ShAd0W20/DAM2_JavaFX/issues">Report Bug</a>
    ·
    <a href="https://github.com/ShAd0W20/DAM2_JavaFX/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

This project is a JavaFX football app that allows you to manage players, teams and player positions with CRUD operations.

# ADVERTISING

Do not use this project for porduction or any real world application. This project have been made for educational purposes only.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

- [![Docker][Docker]][Docker-url]
- [![MySQL][MySQL]][MySQL-url]
- [![Maven][Maven]][Maven]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

Before you begin, ensure you have met the following requirements in order to set up the project and the docker container with a MySQL database:

- docker
- docker-compose
- java 18
- java jdk 18

### Installation

1. Clone the repo

   ```sh
   git clone https://github.com/ShAd0W20/DAM2_JavaFX.git
   ```

2. Install Maven dependencies

3. Enter your database settings in a `.env` file in the root directory

    ```sh
    DB_PORT=
    DB_NAME=
    DB_USER=
    DB_PASSWORD=
    DB_STRING_CONNECTION=
    APP_NAME=
    ```

4. Run docker-compose with the .env file

    ```sh
    docker-compose --env-file .env up -d
    ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->

## License

Distributed under the GNU General Public License v3.0. See `LICENSE.txt` in the root project for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->

## Contact

shad0wstv - contact@shad0wstv.net

Project Link: [https://github.com/ShAd0W20/DAM2_JavaFX](https://github.com/ShAd0W20/DAM2_JavaFX)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[Docker]: https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
[MySQL]: https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white
[MySQL-url]: https://www.mysql.com
[Maven]: https://img.shields.io/badge/apache%20maven-C71A36?style=for-the-badge&logo=apache%20maven&logoColor=white
