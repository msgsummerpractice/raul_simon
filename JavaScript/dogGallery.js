const heading = document.createElement('h1');
heading.textContent = 'Random Dog Gallery';
document.getElementById("container").appendChild(heading);

async function getDogImage() {
  try {
    const response = await fetch('https://dog.ceo/api/breeds/image/random');
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Failed to fetch dog image:', error);
  }
}

const button = document.createElement('button');
button.id = 'myButton';
const btnText = document.createElement('span');
btnText.id = 'btnText';
btnText.textContent = 'Show me a dog!';
button.appendChild(btnText);
document.getElementById("container").appendChild(button);

    button.addEventListener('click', () => {
        button.disabled = true;

        btnText.textContent = 'Loading...';

        const spinner = document.createElement('div');
        spinner.classList.add('spinner');
        button.appendChild(spinner);

        setTimeout(() => {
            button.disabled = false;
            btnText.textContent = 'Show me a dog!';
            spinner.remove();
          let dogImage = document.querySelector('#container img');

        if (!dogImage) {
          dogImage = document.createElement('img');
          dogImage.alt = 'Random Dog';
          document.getElementById("container").insertBefore(dogImage, button);
        }

        getDogImage().then(data => {
          dogImage.src = data.message;
        });
        }, 3000);
        
    });

