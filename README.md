# Code Assignment

## Objective
As a user, you should be able to:
1. **Search for drug record applications** submitted to the FDA for approval.
  - Use the following FDA API: [FDA Drugs API](https://open.fda.gov/apis/drug/drugsfda/how-to-use-the-endpoint/)
  - Implement an API that allows searching by the FDA Manufacturer Name.
  - Results should be paginated for better readability and performance.

2. **Store specific drug record application details** in a local system for future reference.
  - Create and persist the following details for a drug record:
    - Application number (used as ID)
    - Manufacturer name
    - Substance name (if available)

## API Specifications

### 1. `GET /api/drug/search`
- **Description**: Searches for drug record applications by Manufacturer Name using the FDA API.
- **Request Parameters**:
  - `manufacturerName` (mandatory): The name of the drug manufacturer.
  - `limit` (optional, default: 10): Number of results per page.
  - `skip` (optional, default: 0): Number of records to skip (for pagination).
- **Response**: Returns a JSON object containing the search results with pagination details.

### 2. `POST /api/drug`
- **Description**: Persists drug record data in the system.
- **Request Body**:
  - `manufacturerName` (mandatory): The name of the drug manufacturer.
  - `substanceName` (optional, cannot be null): Name of the drug substance. Can be empty but not null.
  - `genericName` (optional, cannot be null): Generic name of the drug. Can be empty but not null.
- **Response**:
  - On success, returns HTTP status `201 Created` without a response body.

### 3. `GET /api/drug`
- **Description**: Fetches a list of drug records that have been stored internally.
- **Request Parameters**:
  - `manufacturerName` (optional): Filter by manufacturer name.
  - `page` (optional, default: 0): Page number for pagination.
  - `size` (optional, default: 10): Number of results per page.
- **Response**: Returns a JSON object with a list of drug records and pagination information.

here is the Postman collection for your reference: [here](Spring-Boot-code-assignment.postman_collection.json) 

## Database Design

- **Database Type**: H2 (In-memory SQL database)
- **Tables**:
  1. **Drug**
    - `application_id` (Primary Key): Unique identifier for the drug application.
    - `manufacturer_name`: Name of the drug manufacturer.
  2. **Drug_Substance_Name**
    - `drug_application_id` (Foreign Key): References `application_id` in the `Drug` table.
    - `substance_name`: Name of the substance related to the drug (One-to-Many relationship with `Drug`).

## Notes
- The `Drug` table stores basic information about the application, while the `Drug_Substance_Name` table captures associated substances.
- Pagination ensures that the search results and internal drug catalog can be efficiently browsed.
