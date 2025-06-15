import React from 'react';
import AssetTable from './AssetTable';
import Stockchart from './Stockchart';
import { Avatar, AvatarImage } from '@/components/ui/avatar';
import { CrossIcon, DotIcon, MessageCircle } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import Chatbot from './Chatboat'; 

function Home() {
  const [category, setCategory] = React.useState('All');
  const [isBotRelease, setIsBotRelease] = React.useState(false);
  const [inputValue, setInputValue] = React.useState("");

  const categories = ['All', 'Top 50', 'Top Gainers', 'Top Losers'];

  const handleCategoryChange = (value) => {
    setCategory(value);
  };

  const handleBotRelease = () => {
    setIsBotRelease(!isBotRelease);
  };

  const handleChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      console.log("Prompt:", inputValue);
      setInputValue(""); // Clear input after pressing Enter
    }
  };

  return (
    <div className="relative">
      <div className="flex flex-col lg:flex-row">
        {/* Left Panel */}
        <div className="lg:w-1/2 lg:border-r border-gray-300 p-3">
          <div className="flex items-center gap-4 flex-wrap">
            {categories.map((cat) => (
              <button
                key={cat}
                onClick={() => handleCategoryChange(cat)}
                className={`px-4 py-2 rounded-full font-semibold transition ${
                  category === cat
                    ? 'bg-blue-600 text-white'
                    : 'border border-gray-400 text-gray-700 hover:bg-gray-100'
                }`}
              >
                {cat}
              </button>
            ))}
          </div>
          <AssetTable />
        </div>

        {/* Right Panel */}
        <div className="lg:w-1/2 p-4 hidden lg:block">
          <Stockchart />

          <div className="flex gap-5 items-center mt-4">
            {/* Avatar */}
            <div>
              <Avatar>
                <AvatarImage src="https://media.istockphoto.com/id/502347558/photo/bitcoin-on-white.jpg?s=612x612&w=0&k=20&c=s8Q2mGv9rq8-lLhq33SzK4RPl5n5wYUwr_66ZBDA6b4=" />
              </Avatar>
            </div>

            {/* Coin Info */}
            <div className="flex items-center gap-2">
              <p>BTC</p>
              <DotIcon className="text-gray-400" />
              <p className="text-gray-400">BitCoin</p>
            </div>

            {/* Price & Change Info */}
            <div className="flex items-center gap-2">
              <p className="font-semibold">₹5,000,000</p>
              <p className="flex text-red-600 gap-2">
                <span>-₹1,234,567,890</span>
                <span>{1234567890}</span>
              </p>
            </div>
          </div>
          <div className='px-4 py-2 start-4 '>
                <Chatbot />
          </div>
            

        </div>
        
        </div>
    </div>
  );
}

export default Home;
