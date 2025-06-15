// import { defineConfig } from 'vite'
// import react from '@vitejs/plugin-react'

// // https://vite.dev/config/
// export default defineConfig({
//   plugins: [react()],
// })



// import path from "path"
// import tailwindcss from "tailwindcss"
// import react from "@vitejs/plugin-react"
// import { defineConfig } from "vite"

// // https://vite.dev/config/
// export default defineConfig({
//   plugins: [react(), tailwindcss()],
//   resolve: {
//     alias: {
//       "@": path.resolve(__dirname, "./src"),
//     },
//   },
//   css: {
//   postcss: { plugins: [require('tailwindcss')] }
// },

// })
import path from 'path'
import { defineConfig } from 'vite'
// import tailwindcss from '@tailwindcss/vite'
import tailwindcss from 'tailwindcss'
import react from '@vitejs/plugin-react'
export default defineConfig({
  plugins: [
    tailwindcss(),
    react(),
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      "@/*": ["./*"]
      
    },
  },
})


