package Handelsakademin.FarmProgram;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Farm {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Animal> animalList = new ArrayList<>();
    ArrayList<Crop> cropList = new ArrayList<>();
    File animalfile = new File("Animals.txt");
    File cropfile = new File("Crops.txt");

    public Farm() {
        if (!cropfile.exists()) {
            cropList.add(new Crop("Tomato", "Cereal", 1));
            cropList.add(new Crop("Corn", "Cereal", 40));
            cropList.add(new Crop("Wheat Field", "Wheat", 100));
            cropList.add(new Crop("Apple Orchard", "Fruit", 100));
            cropList.add(new Crop("Sunflower", "Flower", 30));
        }
        if (!animalfile.exists()) {
            animalList.add(new Animal("Steven", "Horse"));
            animalList.add(new Animal("Kitty", "Cat"));
            animalList.add(new Animal("Ruben", "Cow"));
            animalList.add(new Animal("Ben", "Cow"));
            animalList.add(new Animal("Bob", "Horse"));
        } else {
            Load();
        }
    }

    public void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("What would you like to do?");
            System.out.println("1. View crops.");
            System.out.println("2. Remove crops");
            System.out.println("3. Add crops.");
            System.out.println("4. View animals.");
            System.out.println("5. Add Animals.");
            System.out.println("6. Feed animals.");
            System.out.println("7. Remove animals");
            System.out.println("8. Save");
            System.out.println("9. Quit");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    viewCrops();
                    break;
                case "2":
                    removeCrop();
                    break;
                case "3":
                    addCrop();
                    break;
                case "4":
                    viewAnimals();
                    break;
                case "5":
                    addAnimals();
                    break;
                case "6":
                    feedAnimal();
                    break;
                case "7":
                    removeAnimal();
                    break;
                case "8":
                    save();
                    break;
                case "9":
                    running = false;
                    break;
                default:
                    System.out.println("Choose one of the options.");
                    break;
            }
        }
    }

    private void viewCrops() {
        for (Crop crop : cropList)
            System.out.println(crop.getDescription());
    }

    public void removeCrop() {
        viewCrops();
        boolean foundCropId = false;
        System.out.println("Chose which crop you want to remove (ID): ");
        String inputString = scanner.nextLine().toLowerCase();
        try {
            int input = Integer.parseInt(inputString);
            for (int i = 0; i < cropList.size(); i++) {
                if (input == cropList.get(i).getId()) {
                    foundCropId = true;
                    cropList.remove(i);
                    i--;
                    System.out.println("Crop with id " + input + " has been removed.");
                }
            }
            if (!foundCropId) {
                System.out.println("No crop with that ID was found.");
            }
        } catch (Exception e) {
            System.out.println("Please write a valid input.");
        }
        viewCrops();
    }

    private void addCrop() {
        try {
            viewCrops();
            System.out.println("Enter crop ID to add: ");
            int cropToAdd = Integer.parseInt(scanner.nextLine());

            Crop existingCrop = findCropById(cropToAdd);

            if (existingCrop != null) {
                addQuantityToExistingCrop(existingCrop, cropToAdd);
            } else {
                addNewCrop(cropToAdd);
            }
        } catch (Exception e) {
            System.out.println("Please write a valid input.");
        }
    }

    private void addQuantityToExistingCrop(Crop existingCrop, int cropId) {
        try {
            System.out.print("Crop with ID " + cropId + " already exists. Enter quantity to add: ");
            int quantityToAdd = Integer.parseInt(scanner.nextLine());
            existingCrop.setQuantity(existingCrop.getQuantity() + quantityToAdd);
            System.out.println("Added " + quantityToAdd + " to Crop with ID " + cropId);
            viewCrops();
        } catch (Exception e) {
            System.out.println("Invalid input for quantity.");
        }
    }

    private void addNewCrop(int newCropId) {
        try {
            System.out.print("Enter Crop Name: ");
            String cropName = scanner.nextLine();
            System.out.print("Enter Crop Type: ");
            String cropType = scanner.nextLine();
            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            Crop newCrop = new Crop(cropName, cropType, quantity);
            cropList.add(newCrop);
            System.out.println("New Crop added with ID " + newCropId);
            viewCrops();
        } catch (Exception e) {
            System.out.println("Invalid input for quantity.");
        }
    }

    private void viewAnimals() {
        for (Animal animal : animalList)
            System.out.println(animal.getDescription());
    }

    private boolean addAnimals() {
        viewAnimals();
        System.out.print("Enter name for the new animal: ");
        String name = scanner.nextLine();
        System.out.print("Enter species: ");
        String species = scanner.nextLine();

        Animal newAnimal = new Animal(name, species);
        boolean addedSuccessfully = animalList.add(newAnimal);
        if (addedSuccessfully) {
            System.out.println("New animal added with id " + newAnimal.getId());
            viewAnimals();
        } else {
            System.out.println("Failed to add the new animal.");
        }
        return addedSuccessfully;
    }

    private void feedAnimal() {
        try {
            viewCrops();
            System.out.println("Choose which crop to feed with (ID): ");
            int selectedCropId = Integer.parseInt(scanner.nextLine());

            Crop selectedCrop = findCropById(selectedCropId);

            if (selectedCrop != null && selectedCrop.getQuantity() > 0) {
                viewAnimals();
                System.out.println("Choose which animal (ID) to feed with " + selectedCrop.getName() + ":");
                int selectedAnimalId = Integer.parseInt(scanner.nextLine());

                Animal selectedAnimal = findAnimalById(selectedAnimalId);

                if (selectedAnimal != null) {
                    selectedAnimal.feed(selectedCrop);
                } else {
                    System.out.println("Animal not found.");
                }
            } else if (selectedCrop != null) {
                System.out.println("No more of this crop available to feed.");
            } else {
                System.out.println("Crop not found.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public void removeAnimal() {
        viewAnimals();
        boolean foundAnimalId = false;
        System.out.println("Chose which animal you want to remove (ID): ");
        String inputString = scanner.nextLine().toLowerCase();
        try {
            int input = Integer.parseInt(inputString);
            for (int i = 0; i < animalList.size(); i++) {
                if (input == animalList.get(i).getId()) {
                    foundAnimalId = true;
                    animalList.remove(i);
                    i--;
                    System.out.println("Animal with id " + input + " has been removed.");
                }
            }
            if (!foundAnimalId) {
                System.out.println("No item with that ID was found.");
            }
        } catch (Exception ex) {
            System.out.println("Please write a valid input.");
        }
        viewAnimals();
    }

    private void save() {
        try {
            FileWriter fw = new FileWriter(cropfile);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Crop crop : cropList) {
                bw.write(crop.get_csv());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {

        }

        try {
            FileWriter fw = new FileWriter(animalfile);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Animal animal : animalList) {
                bw.write(animal.get_csv());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {

        }
    }

    private void Load() {
        try {
            FileReader fr = new FileReader(cropfile);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                try {
                    String[] strings = line.split(",");
                    int id = Integer.parseInt(strings[0]);
                    String name = strings[1];
                    String cropType = strings[2];
                    int quantity = Integer.parseInt(strings[3]);

                    Crop crop = new Crop(name, cropType, quantity);
                    cropList.add(crop);
                } catch (Exception e) {

                }
                line = br.readLine();
            } br.close();
        } catch (IOException e) {

        }

        try {
            FileReader fr = new FileReader(animalfile);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                try {
                    String[] strings = line.split(",");
                    int id = Integer.parseInt(strings[0]);
                    String name = strings[1];
                    String species = strings[2];

                    Animal animal = new Animal(name, species);
                    animalList.add(animal);
                } catch (Exception e) {

                }
                line = br.readLine();
            } br.close();
        } catch (IOException e) {

        }
    }

    private Crop findCropById(int newCropId) {
        for (Crop crop : cropList) {
            if (crop.getId() == newCropId) {
                return crop;
            }
        }
        return null;
    }

    private Animal findAnimalById(int animalId) {
        for (Animal animal : animalList) {
            if (animal.getId() == animalId) {
                return animal;
            }
        }
        return null;
    }
}