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
const btnText = document.getElementById("btnText");
btnText.textContent = "Show me a dog!";

button.addEventListener("click", () => {
  button.disabled = true;

  btnText.textContent = "Loading...";

  const spinner = document.getElementById("spinner");
  spinner.style.display = "inline";

  setTimeout(() => {
    button.disabled = false;
    btnText.textContent = "Show me a dog!";
    spinner.style.display = "none";
    let dogImage = document.getElementById("dogImage");

    if (!dogImage) {
      dogImage = document.createElement("img");
      document.getElementById("container").insertBefore(dogImage, button);
    }

    getDogImage().then((data) => {
      dogImage.src = data.message;
      dogImage.style.display = "block";
    });
  }, 3000);
});
