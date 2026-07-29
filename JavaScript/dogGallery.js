const buttonText = "Show me a dog!";

async function getDogImage() {
  try {
    const response = await fetch("https://dog.ceo/api/breeds/image/random");
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Failed to fetch dog image:", error);
  }
}

const button = document.getElementById("myButton");

let dogImage = document.getElementById("dogImage");

button.addEventListener("click", () => {
  button.disabled = true;
  button.innerHTML = "Loading...";

  getDogImage().then((data) => {
    dogImage.src = data.message;
    dogImage.style.display = "block";
     button.disabled = false;
    button.innerHTML = buttonText;
  });
});
