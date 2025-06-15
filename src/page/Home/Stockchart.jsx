import React, { useState } from 'react';
import ReactApexChart from 'react-apexcharts';
import { Button } from '@/components/ui/button';

const timeSeries=[
    {
        keyword:"DIGITAL_CURRENCY_DAILY",
        key : 'Time Series (Daily)',
        lable:"1 Day",
        value:1,
    },
     {
        keyword:"DIGITAL_CURRENCY_DAILY",
        key : 'Weekly Time Series',
        lable:"1 Week",
        value:7,
    },
     {
        keyword:"DIGITAL_CURRENCY_DAILY",
        key : 'Monthly Time Series',
        lable:"1 Month",
        value:30,
    },
     {
        keyword:"DIGITAL_CURRENCY_DAILY",
        key : 'Yearly Time Series',
        lable:"1 Year",
        value:365,
    },
]
const Stockchart = () => {
    const [activeLable , setactivelable]= useState("1 Day")
  const series = [
    {
      data: [
        [1747321490299, 101853.27241327312],
        [1747325095461, 103086.80093931165],
        [1747328851765, 103777.5914622185],
        [1747332574005, 103977.5367463436],
        [1747335782209, 103060.49374205088],
        [1747339499415, 103087.36701019295],
        [1747343097432, 103429.09594246546],
        [1747346692893, 103169.59627070383],
        [1747350530326, 103553.93506366151],
        [1747354192942, 103782.3671140446],
        [1747357799990, 103812.14447323454],
        [1747361386666, 104095.83301023155],
        [1747364677759, 104209.18515019871],
        [1747368295983, 104106.84509644518],
        [1747372193147, 103921.6763892081],
        [1747375712591, 103957.37600357285]
      ]
    }
  ];

  const options = {
    chart: {
      id: "area-datetime",
      type: "area",
      height: 350,
      zoom: {
        autoScaleYaxis: true
      }
    },
    dataLabels: {
      enabled: false
    },
    xaxis: {
      type: 'datetime',
      tickAmount: 6
    },
    colors:[],
    markers: {
      colors: ["#fff"],
      strokeColors: "#fff",
      strokeWidth: 1,
      size: 0,
      style: "hollow"
    },
    tooltip: {
      theme: "dark"
    },
    fill: {
      type: "gradient",
      gradient: {
        shadeIntensity: 1,
        opacityFrom: 0.7,
        opacityTo: 0.9,
        stops: [0, 100]
      }
    },
    grid: {
      borderColor: "#47535E",
      strokeDashArray: 4,
      show: true
    }
  };

  const handleActiveLable=(value)=>{
    setactivelable(value);

  }
  return (
    <div>
        <div className='space-x-3'>
            {timeSeries.map((item)=>
            <Button 
            variant ={activeLable==item.lable?"": "outline"}
            onClick={()=>{handleActiveLable(item.lable)}}
             key={item.lable} >
                {item.lable}

            </Button>)}
        </div>
      <div id="chart-timelines">
        <ReactApexChart
          options={options}
          series={series}       
          type="area"          
          height={350}        
        />
      </div>
    </div>
  );
};

export default Stockchart;
