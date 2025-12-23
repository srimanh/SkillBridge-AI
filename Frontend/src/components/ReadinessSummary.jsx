import React from 'react';
import { ShieldCheck, Target, AlertCircle, Sparkles, ChevronRight, Award } from 'lucide-react';
import { clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';

function cn(...inputs) {
    return twMerge(clsx(inputs));
}

export const ReadinessSummary = ({ summaryData, onRestart }) => {
    const { overallScore, readinessLevel, strengthAreas, weakAreas, summary } = summaryData;

    const getAppearance = (level) => {
        switch (level) {
            case 'Interview Ready':
                return { color: 'text-emerald-500', bg: 'bg-emerald-500/10', border: 'border-emerald-500/20', icon: Award };
            case 'Almost Ready':
                return { color: 'text-indigo-500', bg: 'bg-indigo-500/10', border: 'border-indigo-500/20', icon: ShieldCheck };
            case 'Needs Improvement':
                return { color: 'text-amber-500', bg: 'bg-amber-500/10', border: 'border-amber-500/20', icon: Target };
            default:
                return { color: 'text-rose-500', bg: 'bg-rose-500/10', border: 'border-rose-500/20', icon: AlertCircle };
        }
    };

    const style = getAppearance(readinessLevel);
    const StatusIcon = style.icon;

    return (
        <div className="max-w-4xl mx-auto space-y-12 py-12 animate-in fade-in slide-in-from-bottom-8 duration-1000">
            {/* Main Score Header */}
            <div className="text-center space-y-6">
                <div className="inline-block relative">
                    <div className={cn("w-40 h-40 rounded-full flex items-center justify-center border-[8px]", style.border)}>
                        <span className="text-6xl font-black">{overallScore}</span>
                    </div>
                    <div className={cn("absolute -bottom-4 left-1/2 -translate-x-1/2 px-6 py-2 rounded-full border-2 font-black text-sm uppercase tracking-widest whitespace-nowrap shadow-xl", style.bg, style.color, style.border)}>
                        {readinessLevel}
                    </div>
                </div>
                <h2 className="text-4xl font-bold tracking-tight pt-4">Final Readiness Report</h2>
            </div>

            {/* AI Summary Card */}
            <div className="glass p-10 rounded-[40px] relative overflow-hidden group">
                <div className="absolute top-0 right-0 p-10 opacity-5 group-hover:opacity-10 transition-opacity">
                    <Sparkles size={120} />
                </div>
                <div className="relative space-y-4">
                    <div className="flex items-center gap-2 text-indigo-500 text-xs font-black uppercase tracking-widest">
                        <Sparkles size={16} /> AI Executive Summary
                    </div>
                    <p className="text-xl md:text-2xl leading-relaxed font-medium">
                        "{summary}"
                    </p>
                </div>
            </div>

            {/* Strengths & Weaknesses Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                {/* Strengths */}
                <div className="space-y-4">
                    <h3 className="text-sm font-black uppercase tracking-widest opacity-40 px-2 flex items-center gap-2">
                        <ShieldCheck size={16} className="text-emerald-500" /> Key Strengths
                    </h3>
                    <div className="space-y-3">
                        {strengthAreas.length > 0 ? (
                            strengthAreas.map((skill, i) => (
                                <div key={i} className="flex items-center gap-4 p-5 rounded-3xl bg-emerald-500/5 border border-emerald-500/10">
                                    <div className="w-2 h-2 rounded-full bg-emerald-500" />
                                    <span className="font-bold">{skill}</span>
                                </div>
                            ))
                        ) : (
                            <p className="text-sm opacity-50 px-2">No significant strengths identified yet.</p>
                        )}
                    </div>
                </div>

                {/* Weaknesses */}
                <div className="space-y-4">
                    <h3 className="text-sm font-black uppercase tracking-widest opacity-40 px-2 flex items-center gap-2">
                        <Target size={16} className="text-rose-500" /> Growth Areas
                    </h3>
                    <div className="space-y-3">
                        {weakAreas.length > 0 ? (
                            weakAreas.map((skill, i) => (
                                <div key={i} className="flex items-center gap-4 p-5 rounded-3xl bg-rose-500/5 border border-rose-500/10">
                                    <div className="w-2 h-2 rounded-full bg-rose-500" />
                                    <span className="font-bold">{skill}</span>
                                </div>
                            ))
                        ) : (
                            <p className="text-sm opacity-50 px-2">No major weaknesses identified. Great job!</p>
                        )}
                    </div>
                </div>
            </div>

            {/* Call to Action */}
            <div className="pt-8 text-center">
                <button
                    onClick={onRestart}
                    className="group inline-flex items-center gap-3 px-10 py-5 bg-foreground text-background rounded-full font-black text-lg hover:scale-105 transition-all shadow-2xl shadow-indigo-500/20"
                >
                    Start New Analysis
                    <ChevronRight className="group-hover:translate-x-1 transition-transform" />
                </button>
            </div>
        </div>
    );
};
