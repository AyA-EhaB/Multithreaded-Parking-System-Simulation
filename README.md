# Multithreaded-Parking-System-Simulation
Here's a GitHub-friendly version of the visualization for your parking system simulation. This can be used in the README file to explain the project's flow and architecture.

---

# Multithreaded Parking System Simulation

This project simulates a multithreaded parking system using Java threads and semaphores. The simulation models a parking lot with multiple entry gates, where cars arrive, park for a specified duration, and then leave. Each car’s journey (arrival, parking, and departure) is managed through a separate thread.

## Process Flow

### 1. Input Handling
- The input file contains car information in the format:
  ```
  Gate <gate_number>, Car <car_id>, Arrive <arrival_time>, Parks <parking_duration>
  ```
- Each line represents a car, specifying:
  - **Gate Number**: Gate through which the car arrives
  - **Car ID**: Unique identifier for the car
  - **Arrival Time**: Scheduled arrival time (in arbitrary time units)
  - **Parking Duration**: Duration the car will park (in arbitrary time units)
  
Example:
```
Gate 1, Car 0, Arrive 0, Parks 3
Gate 2, Car 1, Arrive 1, Parks 2
```

### 2. Car and Gate Thread Setup
- For each car entry, a `Car_Thread` is created, representing the car's actions.
- Each car thread is initialized with:
  - Gate number
  - Car ID
  - Arrival time
  - Parking duration
- Threads are started to handle car arrival, parking, and departure concurrently.

### 3. Arrival and Parking Process
- Each car thread:
  - Waits (sleeps) until its scheduled arrival time.
  - Attempts to park when it "arrives":
    - If a spot is available, it parks.
    - If all spots are occupied, it waits until a spot becomes free.

### 4. Parking Occupancy Management
- Parking spots are managed using semaphores:
  - **Semaphore count** = number of available parking spots.
  - When a car parks, the semaphore count decreases.
  - When a car leaves, the semaphore count increases, signaling that a spot is now available.

### 5. Departure and Spot Release
- After parking for the specified duration, the car "leaves," freeing up a spot.
- The semaphore is released, allowing waiting cars to attempt parking.

### 6. Output and Logging
- During each key action, logs are printed to track:
  - Arrival time
  - Parking start
  - Parking duration
  - Departure time

Example log output:
```
Car 0 from Gate 1 arrived at time 0
Car 0 from Gate 1 is parking for 3 units
Car 1 from Gate 2 arrived at time 1
Car 1 from Gate 2 is parking for 2 units
Car 0 from Gate 1 left after 3 units
...
```

## Flow Diagram

```plaintext
1. Read Input File ─────────────► 2. Create Car Threads ─────────► 3. Start Car Arrival

                            ▼                                    ▼

+--------------------------------------------------------------+
|                                                              |
|                  ** Parking System Simulation **             |
|                                                              |
|  1. Parse Input File                                         |
|     - Extracts car details (gate, ID, arrival, duration)     |
|                                                              |
|  2. Initialize Threads                                       |
|     - Create threads for each car                            |
|                                                              |
|  3. Car Thread Execution                                     |
|     - Car thread sleeps until arrival time                   |
|     - Tries to park when arrival time is reached             |
|                                                              |
|  4. Semaphore Management                                     |
|     - Parking spots controlled by semaphores                |
|     - Blocks if all spots are occupied                      |
|                                                              |
|  5. Car Departure                                            |
|     - After parking, car leaves and frees a spot             |
|                                                              |
|  6. Logging and Output                                       |
|     - Logs car arrival, parking, and departure events        |
+--------------------------------------------------------------+

                            ▼

4. Console Output ─────────────────────────────────────────────► Logs system activity
```

---

## Key Concepts

- **Threads**: Each car has its own thread, simulating concurrent arrivals and parking.
- **Semaphores**: Used to limit parking spots, ensuring no more than four cars are parked simultaneously.
- **Synchronization**: Ensures accurate timing for car arrivals and departures, and prevents race conditions.

---

## Example Usage

To run the simulation:
1. Prepare an input file with the car information in the specified format.
2. Run the main program:
   ```java
   java Parking_Sys input.txt
   ```
3. Observe the console output to see each car’s status in real-time.

---

This README section provides a comprehensive overview and process flow, perfect for helping readers understand the architecture of your parking system simulation project.
