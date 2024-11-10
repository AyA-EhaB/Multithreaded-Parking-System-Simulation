# Multithreaded-Parking-System-Simulation
1. Read Input File  ────────────────►  2. Car Threads Setup
                          │
                          ▼
+-----------------------------------------+
|                                         |
|      ** Parking System Simulation **    |
|                                         |
|  1. Input Handling                      |
|     - Parse input file to read          |
|       each car's gate, arrival time,    |
|       and parking duration.             |
|                                         |
|  2. Gate and Car Thread Creation        |
|     - Each car is assigned to a thread, |
|       which simulates its journey       |
|       through the parking system.       |
|                                         |
|  3. Arrival and Parking Process         |
|     - Car thread waits until its        |
|       scheduled arrival time.           |
|     - If a parking spot is available,   |
|       car parks; else, it waits until   |
|       a spot opens.                     |
|                                         |
|  4. Parking Occupancy Management        |
|     - Semaphores manage parking spots   |
|       so that no more than the          |
|       maximum allowed cars are parked   |
|       at once.                          |
|                                         |
|  5. Departure                           |
|     - After parking duration, car       |
|       leaves, freeing up a spot.        |
|     - Next waiting car (if any) parks.  |
|                                         |
+-----------------------------------------+

                    ▼
3. Output Messages ─────────────────────► Console Logs

  - Each car’s arrival, parking start, parking duration, and departure
    is logged for monitoring purposes.
