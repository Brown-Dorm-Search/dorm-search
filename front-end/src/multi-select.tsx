import { useState } from "react";
import { ChevronUp } from "lucide-react";
import { motion } from "framer-motion";

export default function DropdownFilter({ title, options, selectedValues, onChange }) {
    const [isOpen, setIsOpen] = useState(false);

    const toggleSelection = (value: string) => {
        let updatedValues = [];

        if (value === "All") {
            // If selecting "All", clear everything and select "All"
            updatedValues = selectedValues.includes("All") ? [] : ["All"];
        } else {
            // If selecting a normal option, deselect "All" and toggle the option
            updatedValues = selectedValues.includes(value)
                ? selectedValues.filter((v: string) => v !== value)
                : [...selectedValues.filter((v: string) => v !== "All"), value];
        }

        onChange(updatedValues);
    };

    return (
        <div className="back-multi">
            <div className="title-multi">
                <span className="title_text">{title}</span>
                <button onClick={() => setIsOpen(!isOpen)}
                    className="alt-button">
                    <motion.div
                        animate={{ rotate: isOpen ? 0 : 180 }}
                        transition={{ duration: 0.5, ease: "easeInOut" }}
                    >
                        <ChevronUp size={16} />
                    </motion.div>
                </button>
            </div>

            {isOpen && (
                <><div className="options-multi">
                    {options.map(({ value, label }) => (
                        <label key={value} className="option-label">
                            <input
                                type="checkbox"
                                checked={selectedValues.includes(value)}
                                onChange={() => toggleSelection(value)}
                            />
                            <span>{label}</span>
                        </label>
                    ))}
                </div><div className="bottom_buttons">
                        <button onClick={() => onChange([])} className='alt-button'>Clear</button>
                        <button onClick={() => setIsOpen(!isOpen)} className='alt-button'>Done</button>
                    </div></>
            )}
        </div>
    );
}
