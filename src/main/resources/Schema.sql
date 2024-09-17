-- Drop the drug table if it exists
DROP TABLE IF EXISTS drug_substance_name;
DROP TABLE IF EXISTS drug;

-- Main table for CustomDrug
CREATE TABLE drug (
                      application_id UUID PRIMARY KEY,      -- UUID as the primary key
                      manufacturer_name VARCHAR(255)        -- Manufacturer name
);

-- Separate table for the List of substance names
CREATE TABLE drug_substance_name (
                                     drug_application_id UUID,              -- Foreign key reference to drug.application_id
                                     substance_name VARCHAR(255),           -- Substance name in the list
                                     CONSTRAINT fk_drug FOREIGN KEY (drug_application_id) REFERENCES drug(application_id)
);

-- Optional: Create an index for faster querying on drug_application_id
CREATE INDEX idx_drug_application_id
    ON drug_substance_name (drug_application_id);
