const button = document.querySelector<HTMLButtonElement>("#myButton");
const image: HTMLImageElement | null = document.querySelector("#dogImage");

if(!button || !image) {
  throw new Error("Button or image element not found in the DOM.");
}

type DogApiResponse ={
    message: string;
}


const url = "https://dog.ceo/api/breeds/image/random";

async function fetchDogImage(url: string): Promise<void> {
  try {
    let response = await fetch(url);
    let data: DogApiResponse = await response.json();
    if (image) {
      image.src = data.message;
      image.style.display = "block";
    }
  } catch (error) {
    console.error("Error fetching dog image:", error);
  }
}

async function startLoading() {
  if (!button) return;
  button.setAttribute("disabled", "true");
  button.innerHTML = "Loading...";
}

async function stopLoading() {
  if (!button) return;
  button.removeAttribute("disabled");
  button.innerHTML = "Show me a dog!";
}

button?.addEventListener("click", async () => {
  await startLoading();
  await fetchDogImage(url);
  await stopLoading();
});
