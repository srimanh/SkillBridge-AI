import React, { useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { AlertCircle, CheckCircle2, TrendingUp, XCircle } from 'lucide-react';
import { clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';

function cn(...inputs) {
    return twMerge(clsx(inputs));
}

export const AnalysisResults = ({ results }) => {
    const containerRef = useRef(null);

    useEffect(() => {
        if (results) {
            gsap.fromTo(
                containerRef.current.children,
                { opacity: 0, y: 20 },
                { opacity: 1, y: 0, duration: 0.6, stagger: 0.1, ease: 'power3.out' }
            );
        }
    }, [results]);

    if (!results) return null;

    const { matchScore, missingSkills, weakSections, sectionFeedback } = results;

    return (
        <div ref={containerRef} className="space-y-8 mt-12 pb-20">
            {/* Score Section */}
            <div className="glass p-8 rounded-3xl relative overflow-hidden group">
                <div className="absolute top-0 right-0 p-8 opacity-10 group-hover:opacity-20 transition-opacity">
                    <TrendingUp size={120} />
                </div>
                <h3 className="text-sm font-medium uppercase tracking-widest text-indigo-500 mb-2">Match Score</h3>
                <div className="flex items-baseline gap-4">
                    <span className="text-7xl font-bold tracking-tighter">{matchScore}%</span>
                    <div className="h-2 w-full bg-slate-200 dark:bg-neutral-800 rounded-full overflow-hidden flex-1 max-w-xs">
                        <div
                            className="h-full bg-indigo-500 transition-all duration-1000 ease-out"
                            style={{ width: `${matchScore}%` }}
                        />
                    </div>
                </div>
                <p className="mt-4 text-slate-500 dark:text-neutral-400 max-w-md">
                    {matchScore > 80 ? "Excellent match! Your profile aligns closely with the role requirements." :
                        matchScore > 50 ? "Good foundation, but there are some critical gaps to address." :
                            "Significant gaps detected. Consider refining your resume based on the feedback below."}
                </p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                {/* Missing Skills */}
                <div className="glass p-8 rounded-3xl">
                    <div className="flex items-center gap-3 mb-6 text-rose-500">
                        <XCircle size={24} />
                        <h3 className="text-xl font-semibold">Missing Skills</h3>
                    </div>
                    <div className="flex flex-wrap gap-2">
                        {missingSkills.map((skill, i) => (
                            <span key={i} className="px-4 py-2 bg-rose-50 dark:bg-rose-950/20 text-rose-600 dark:text-rose-400 rounded-xl text-sm font-medium border border-rose-100 dark:border-rose-900/30">
                                {skill}
                            </span>
                        ))}
                    </div>
                </div>

                {/* Weak Sections */}
                <div className="glass p-8 rounded-3xl">
                    <div className="flex items-center gap-3 mb-6 text-amber-500">
                        <AlertCircle size={24} />
                        <h3 className="text-xl font-semibold">Weak Sections</h3>
                    </div>
                    <div className="flex flex-wrap gap-2">
                        {weakSections.map((section, i) => (
                            <span key={i} className="px-4 py-2 bg-amber-50 dark:bg-amber-950/20 text-amber-600 dark:text-amber-400 rounded-xl text-sm font-medium border border-amber-100 dark:border-amber-900/30">
                                {section}
                            </span>
                        ))}
                    </div>
                </div>
            </div>

            {/* Section Feedback */}
            <div className="glass p-8 rounded-3xl">
                <div className="flex items-center gap-3 mb-8 text-indigo-500">
                    <CheckCircle2 size={24} />
                    <h3 className="text-xl font-semibold">Detailed Feedback</h3>
                </div>
                <div className="space-y-6">
                    {Object.entries(sectionFeedback).map(([section, feedback], i) => (
                        <div key={i} className="border-b border-slate-100 dark:border-neutral-800 last:border-0 pb-6 last:pb-0">
                            <h4 className="text-sm font-bold uppercase tracking-wider mb-2 opacity-50">{section}</h4>
                            <p className="text-lg leading-relaxed">{feedback}</p>
                        </div>
                    ))}
                </div>
            </div>

            {/* Next Step Action */}
            <div className="flex justify-center pt-8">
                <button className="px-8 py-4 bg-indigo-500 hover:bg-indigo-600 text-white rounded-2xl font-bold text-lg transition-all hover:scale-105 active:scale-95 shadow-xl shadow-indigo-500/20">
                    Start Targeted Mock Interview
                </button>
            </div>
        </div>
    );
};
