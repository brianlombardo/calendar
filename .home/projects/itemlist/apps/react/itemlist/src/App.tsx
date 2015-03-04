import React, {useState, useEffect} from 'react';
import axios from 'axios';

interface Item {
  id: number;
  data: string;
}

const App: React.FC = () => {
  const [inputText, setInputText] = useState('');
  const [itemDataList, setItemDataList] = useState<Item[]>([]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputText(e.target.value);
  };

  const fetchItemData = async () => {
    try {
      const response = await axios.get<Item[]>('http://localhost:3001/items');
      setItemDataList(response.data);
    } catch (error) {
      console.error('Error fetching Item:', error);
    }
  };

  const handleAddItemData = async () => {
    if (!inputText.trim()) return;
    try {
      await axios.post('http://localhost:3001/items', {data: inputText});
      setInputText('');
      fetchItemData();
    } catch (error) {
      console.error('Error adding Item:', error);
    }
  };

  useEffect(() => {
    fetchItemData();
  }, []);

  return (
    <div style={{padding: '20px'}}>
      <h1>Item List</h1>

      <div>
        <input
          type="text"
          value={inputText}
          onChange={handleInputChange}
          placeholder="Enter Item"
          style={{padding: '10px', width: '300px', marginRight: '10px'}}
        />
        <button onClick={handleAddItemData} style={{padding: '10px'}}>
          Add
        </button>
      </div>

      <ul>
        {itemDataList.length > 0 ? (
          itemDataList.map((item) => (
            <li key={item.id} style={{padding: '5px 0'}}>
              {item.data}
            </li>
          ))
        ) : (
          <li>No data available</li>
        )}
      </ul>
    </div>
  );
};

export default App;
