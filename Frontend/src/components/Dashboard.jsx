import React, { useState, useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { AnalysisResults } from './AnalysisResults';
import { useTheme } from '../context/ThemeContext';
import { Sun, Moon, Sparkles, Upload, Loader2, FileText, Briefcase } from 'lucide-react';

gsap.registerPlugin(ScrollTrigger);

export const Dashboard = () => {
    const { isDark, toggleTheme } = useTheme();
    const [resumeText, setResumeText] = useState('');
    const [jobDescription, setJobDescription] = useState('');
    const [loading, setLoading] = useState(false);
    const [results, setResults] = useState(null);
    const [error, setError] = useState(null);

    const heroRef = useRef(null);
    const formRef = useRef(null);

    useEffect(() => {
        const ctx = gsap.context(() => {
            gsap.from(heroRef.current, {
                opacity: 0,
                y: 30,
                duration: 1,
                ease: 'power4.out'
            });

            gsap.from(formRef.current, {
                opacity: 0,
                y: 50,
                duration: 1,
                delay: 0.3,
                ease: 'power4.out'
            });
        });
        return () => ctx.revert();
    }, []);

    const handleAnalyze = async () => {
        if (!resumeText || !jobDescription) {
            setError('Please provide both resume text and job description.');
            return;
        }

        setLoading(true);
        setError(null);
        setResults(null);

        try {
            const response = await fetch('http://localhost:8080/api/analyze-resume', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ resumeText, jobDescription }),
            });

            if (!response.ok) throw new Error('Failed to analyze resume. Please try again.');

            const data = await response.json();
            setResults(data);

            // Scroll to results
            setTimeout(() => {
                window.scrollTo({ top: window.innerHeight - 100, behavior: 'smooth' });
            }, 100);

        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen transition-colors duration-500">
            {/* Navigation */}
            <nav className="fixed top-0 w-full z-50 px-6 py-6 flex justify-between items-center glass">
                <div className="flex items-center gap-2">
                    <div className="w-10 h-10 bg-indigo-500 rounded-xl flex items-center justify-center text-white shadow-lg shadow-indigo-500/40">
                        <Sparkles size={20} />
                    </div>
                    <span className="text-xl font-bold tracking-tight">SkillBridge AI</span>
                </div>
                <button
                    onClick={toggleTheme}
                    className="p-3 rounded-2xl hover:bg-slate-100 dark:hover:bg-neutral-800 transition-colors"
                >
                    {isDark ? <Sun size={20} /> : <Moon size={20} />}
                </button>
            </nav>

            <main className="max-w-6xl mx-auto px-6 pt-32">
                {/* Hero Section */}
                <section ref={heroRef} className="text-center mb-20">
                    <h1 className="text-6xl md:text-8xl font-bold tracking-tighter mb-6 bg-gradient-to-b from-foreground to-foreground/50 bg-clip-text text-transparent">
                        Bridge the gap to <br /> your next role.
                    </h1>
                    <p className="text-xl text-slate-500 dark:text-neutral-400 max-w-2xl mx-auto leading-relaxed">
                        Upload your resume and the job description. Our AI analyzes alignment,
                        identifies gaps, and prepares you for the interview.
                    </p>
                </section>

                {/* Input Section */}
                <div ref={formRef} className="grid grid-cols-1 md:grid-cols-2 gap-8 mb-20">
                    <div className="space-y-4">
                        <div className="flex items-center gap-2 text-sm font-semibold uppercase tracking-wider opacity-60">
                            <FileText size={16} />
                            <span>Resume Content</span>
                        </div>
                        <textarea
                            value={resumeText}
                            onChange={(e) => setResumeText(e.target.value)}
                            placeholder="Paste your resume text here..."
                            className="w-full h-80 p-6 rounded-3xl bg-surface border border-border focus:ring-2 focus:ring-indigo-500/20 focus:outline-none resize-none transition-all placeholder:opacity-30"
                        />
                    </div>

                    <div className="space-y-4">
                        <div className="flex items-center gap-2 text-sm font-semibold uppercase tracking-wider opacity-60">
                            <Briefcase size={16} />
                            <span>Job Description</span>
                        </div>
                        <textarea
                            value={jobDescription}
                            onChange={(e) => setJobDescription(e.target.value)}
                            placeholder="Paste the job description here..."
                            className="w-full h-80 p-6 rounded-3xl bg-surface border border-border focus:ring-2 focus:ring-indigo-500/20 focus:outline-none resize-none transition-all placeholder:opacity-30"
                        />
                    </div>

                    <div className="md:col-span-2 flex flex-col items-center gap-4 mt-4">
                        {error && (
                            <p className="text-rose-500 text-sm font-medium animate-bounce">{error}</p>
                        )}
                        <button
                            onClick={handleAnalyze}
                            disabled={loading}
                            className="w-full max-w-md py-5 bg-foreground text-background rounded-2xl font-bold text-lg hover:scale-[1.02] active:scale-[0.98] transition-all disabled:opacity-50 disabled:hover:scale-100 flex items-center justify-center gap-3 shadow-2xl"
                        >
                            {loading ? (
                                <>
                                    <Loader2 className="animate-spin" size={20} />
                                    Analyzing Alignment...
                                </>
                            ) : (
                                'Analyze Resume'
                            )}
                        </button>
                    </div>
                </div>

                {/* Results Section */}
                {results && <AnalysisResults results={results} />}
            </main>

            {/* Subtle Background Elements */}
            <div className="fixed top-0 left-0 w-full h-full -z-10 pointer-events-none opacity-20 dark:opacity-10">
                <div className="absolute top-[10%] left-[10%] w-[40%] h-[40%] bg-indigo-500/20 rounded-full blur-[120px]" />
                <div className="absolute bottom-[10%] right-[10%] w-[40%] h-[40%] bg-indigo-400/20 rounded-full blur-[120px]" />
            </div>
        </div>
    );
};
