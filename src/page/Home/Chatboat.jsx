import React from 'react'
import { Avatar, AvatarImage } from '@/components/ui/avatar';
import { CrossIcon, DotIcon, MessageCircle } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';

function Chatboat() {
  const [isBotRelease, setIsBotRelease] = React.useState(false);
  const [inputValue, setInputValue] = React.useState("");



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
    <div>
             {/* Chat Bot Section */}
          {isBotRelease && (
            <section className="absolute top-9 bottom-0 right-0 z-40 flex-col flex justify-end items-end gap-2">
              <div className="rounded-md w-[20rem] md:w-[25rem] lg:w-[30rem] h-[70vh] bg-slate-900 shadow-lg">
                {/* Header */}
                <div className="flex justify-between items-center border-b px-6 h-10 text-white">
                  <p>Chat Bot</p>
                  <Button onClick={handleBotRelease} variant="ghost" size="icon">
                    <CrossIcon />
                  </Button>
                </div>

                {/* Chat Messages */}
                <div className="h-[76%] overflow-y-auto px-5 flex flex-col gap-5 py-2 scroll-container">
                  <div className="self-start w-auto">
                    <div className="px-3 py-2 rounded-md bg-slate-800 text-white w-fit max-w-[80%]">
                      <p>Hi Rohit Bramhankar</p>
                      <p>You can ask any question related to cryptocurrency.</p>
                      <p>Like price, market cap, etc...</p>
                    </div>
                  </div>

                  {[1, 1, 1, 1].map((item, i) => (
                    <div key={i} className={`${i % 2 === 0 ? "self-start" : "self-end"} pb-1 w-auto`}>
                      <div className="px-3 py-2 rounded-md bg-slate-800 text-white w-fit max-w-[80%]">
                        <p>{i % 2 === 0 ? "prompt: Hi Rohit Bramhankar" : "ans: Hi Rohit Bramhankar"}</p>
                      </div>
                    </div>
                  ))}
                </div>

                {/* Input */}
                <div className="p-2 bg-slate-800">
                  <Input
                    className="w-full border-none outline-none text-white bg-slate-700"
                    placeholder="Write a prompt..."
                    onChange={handleChange}
                    value={inputValue}
                    onKeyPress={handleKeyPress}
                  />
                </div>
              </div>
            </section>
          )}

          {/* Chat Bot Toggle Button */}
          <div className="relative cursor-pointer group w-[10rem] mt-4">
            <Button
              onClick={handleBotRelease}
              variant="ghost"
              className="flex justify-center items-center bg-white hover:bg-gray-100 rounded-md shadow-md group-hover:bg-gray-200 transition-all duration-300 ease-in-out"
            >
              <MessageCircle className="fill-[#3c495f] -rotate-90 stroke-none group-hover:fill-[#1a1a1a]" />
              <span className="ml-2 text-2xl">Chat Bot</span>
            </Button>
          </div>
     </div>
    



   
  )
}

export default Chatboat
